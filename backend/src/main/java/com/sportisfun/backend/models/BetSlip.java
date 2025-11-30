package com.sportisfun.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BetSlip {

    @Id
    @GeneratedValue
    private Long id;

    @Min(value = 1, message = "Minimal bet stake is $1")
    @Column(precision = 12, scale=2)
    @Positive
    private BigDecimal stake;

    @Positive
    @Column(precision = 14, scale=2)
    private BigDecimal potentialWin;

    @Min(1)
    @Column(precision = 14, scale=2, nullable=false)
    private BigDecimal totalOdds;

    @Enumerated(EnumType.STRING)
    @Column(length = 16, nullable=false)
    private BetSlipStatus status = BetSlipStatus.DRAFT;

    private LocalDateTime placedAt;

    private LocalDateTime settledAt;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(
            mappedBy = "betSlip",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<BetPick> betPicks = new LinkedHashSet<>();


    public void addPick(BetPick pick) {
        if(status != BetSlipStatus.DRAFT) throw new IllegalStateException("Cannot add bet pick to existing coupon");
        pick.setBetSlip(this);
        betPicks.add(pick);
        recalcTotals();
    }

    public void removePick(BetPick pick) {
        if(status != BetSlipStatus.DRAFT) throw new IllegalStateException("Cannot remove bet pick from placed coupon");
        betPicks.removeIf(p -> Objects.equals(p.getId(), pick.getId()));
        recalcTotals();
    }

    public void place(BigDecimal stake){
        if(status != BetSlipStatus.DRAFT) throw new IllegalStateException("Cannot place bet slip to placed coupon");
        this.stake = stake;
        recalcTotals();
        this.potentialWin = stake.multiply(totalOdds);
        this.status = BetSlipStatus.PLACED;
        this.placedAt = LocalDateTime.now();
    }

    public void settle(){
        if(status != BetSlipStatus.PLACED) throw new IllegalStateException("Coupon is not PLACED");
        boolean allResolved = betPicks.stream().allMatch(p -> p.getResult() != BetPickResult.PENDING);
        boolean allWin = getBetPicks().stream()
                .allMatch(p -> p.getResult() == BetPickResult.WIN);
        if(!allResolved) return;
        if(allWin){
            this.status = BetSlipStatus.WON;
        }else{
            this.status = BetSlipStatus.LOST;
        }
        settledAt = LocalDateTime.now();
    }

    public void recalcTotals() {
        this.totalOdds = betPicks.stream()
                .map(p -> p.getSelectedOdd())
                .reduce(BigDecimal.ONE, BigDecimal::multiply);
        if(stake != null){
            potentialWin = stake.multiply(totalOdds);
        }
    }

}

package com.sportisfun.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    @Column(nullable = false)
    private BigDecimal stake;

    @Positive
    private BigDecimal potentialWin;

    @Min(1)
    private BigDecimal totalOdds;

    private boolean won;

    private LocalDateTime placedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(
            mappedBy = "betSlip",
            cascade = CascadeType.ALL
    )
    private List<BetPick> betPicks;
}

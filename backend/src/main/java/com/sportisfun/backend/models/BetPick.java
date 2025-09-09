package com.sportisfun.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BetPick {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @Enumerated(EnumType.STRING)
    private BetOption betOption;

    @Column(precision = 8, scale = 2, nullable = false)
    private BigDecimal selectedOdd;

    @Enumerated(EnumType.STRING)
    @Column(length = 16, nullable = false)
    private BetPickResult result = BetPickResult.PENDING;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "betSlip_id")
    private BetSlip betSlip;
}

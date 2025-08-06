package com.sportisfun.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

@Entity
public class BetPick {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    @Enumerated(EnumType.STRING)
    private BetOption betOption;

    @Min(value = 1)
    private BigDecimal odd;

    private boolean correct;


    @ManyToOne
    @JoinColumn(name = "betSlip_id")
    private BetSlip betSlip;
}

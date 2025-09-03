package com.sportisfun.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Odds {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="match_id", nullable=false, unique=true)
    private Match match;

    @Column(precision=8, scale=2, nullable=false)
    private BigDecimal homeWin;
    @Column(precision=8, scale=2, nullable=false)
    private BigDecimal draw;
    @Column(precision=8, scale=2, nullable=false)
    private BigDecimal awayWin;
}

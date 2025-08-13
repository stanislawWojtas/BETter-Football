package com.sportisfun.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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

    @OneToOne
    private Match match;

    @Min(1)
    private BigDecimal homeWin;
    @Min(1)
    private BigDecimal draw;
    @Min(1)
    private BigDecimal awayWin;
}

package com.sportisfun.backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchOddsDto {
    private Long id;
    private String homeTeam;
    private String awayTeam;
    private LocalDateTime date;
    private String league;

    // for odds
    private BigDecimal homeWin;
    private BigDecimal awayWin;
    private BigDecimal draw;
}

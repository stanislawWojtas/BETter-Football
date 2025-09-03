package com.sportisfun.backend.DTOs;

import com.sportisfun.backend.models.BetSlipStatus;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Value
@Builder
public class BetSlipDto {
    Long id;
    BetSlipStatus status;
    BigDecimal stake;
    BigDecimal totalOdds;
    BigDecimal totalWin;
    BigDecimal potentialWin;
    LocalDateTime placedAt;
    LocalDateTime settledAt;
    Set<BetPickDto> betPicks;
}

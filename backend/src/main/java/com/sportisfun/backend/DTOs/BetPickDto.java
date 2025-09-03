package com.sportisfun.backend.DTOs;

import com.sportisfun.backend.models.BetOption;
import com.sportisfun.backend.models.BetPickResult;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Builder
@Value
public class BetPickDto {
    Long id;
    Long matchId;
    BetOption option;
    BigDecimal selectedOdds;
    BetPickResult result;
}

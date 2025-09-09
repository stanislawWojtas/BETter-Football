package com.sportisfun.backend.DTOs;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlaceBetSlipRequest {
    @NotNull
    @DecimalMin("1.00")
    private BigDecimal stake;
}

package com.sportisfun.backend.DTOs;

import com.sportisfun.backend.models.BetOption;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddPickDto {
    @NotNull
    private Long matchId;
    @NotNull
    private BetOption betOption;
}

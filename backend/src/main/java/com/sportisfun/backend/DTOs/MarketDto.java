package com.sportisfun.backend.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MarketDto {
    private String key;
    @JsonProperty("last_update")
    private String lastUpdate;
    private List<OutcomeDto> outcomes;
}

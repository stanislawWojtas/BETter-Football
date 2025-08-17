package com.sportisfun.backend.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BookmakerDto {
    private String key;
    private String title;
    @JsonProperty("last_update")
    private String lastUpdate;
    private List<MarketDto> markets;
}

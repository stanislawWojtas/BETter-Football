package com.sportisfun.backend.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MatchResultApiResponse {

    @JsonProperty("id")
    private String id;
    @JsonProperty("sport_key")
    private String sportKey;
    @JsonProperty("sport_title")
    private String sportTitle;
    @JsonProperty("commence_time")
    private String commenceTime;
    @JsonProperty("completed")
    private boolean completed;
    @JsonProperty("home_team")
    private String homeTeam;
    @JsonProperty("away_team")
    private String awayTeam;
    private List<ScoreDto> scores;
    @JsonProperty("last_update")
    private String lastUpdate;
}

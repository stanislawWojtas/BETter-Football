package com.sportisfun.backend.DTOs;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MatchOddsApiResponse {

    @JsonProperty("id")
    private String id;
    @JsonProperty("sport_key")
    private String sportKey;
    @JsonProperty("sport_title")
    private String sportTitle;
    @JsonProperty("commence_time")
    private String commenceTime;
    @JsonProperty("home_team")
    private String homeTeam;
    @JsonProperty("away_team")
    private String awayTeam;
    private List<BookmakerDto> bookmakers;
}

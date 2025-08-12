package com.sportisfun.backend.services;

import com.sportisfun.backend.DTOs.MatchOddsApiResponse;
import com.sportisfun.backend.models.LeagueConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportService {

    private final MatchService matchService;
    private final OddsApiClient oddsApiClient;

    public void importOddsData(String leagueId, String leagueRequestName){
        try{
            LeagueConfig leagueConfig = LeagueConfig.valueOf(leagueId.toUpperCase());
            List<MatchOddsApiResponse> matches = oddsApiClient.getOdds(
                    leagueRequestName,
                    "eu",
                    "h2h",
                    "onexbet"
            );
            matchService.importFromApi(matches, leagueConfig.getName(), leagueConfig.getCountry());
            System.out.println("Successfully imported odds data for league: " + leagueConfig.getName());
        }catch (Exception e){
            System.out.println("Error importing odds data for league " + leagueId + ": " + e.getMessage());
        }
    }

}

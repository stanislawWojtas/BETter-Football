package com.sportisfun.backend.services;

import com.sportisfun.backend.DTOs.MatchOddsApiResponse;
import com.sportisfun.backend.DTOs.MatchResultApiResponse;
import com.sportisfun.backend.models.LeagueConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportService {

    private final MatchService matchService;
    private final OddsApiClient oddsApiClient;
    private final MatchResultsApiClient matchResultsApiClient;
    private final ResultService resultService;

    private final static Logger logger = LoggerFactory.getLogger(ImportService.class);

    @Value("${bookmaker.key}")
    private String bookmaker;

    public void importOddsData(String leagueId, String leagueRequestName){
        try{
            LeagueConfig leagueConfig = LeagueConfig.valueOf(leagueId.toUpperCase());
            List<MatchOddsApiResponse> matches = oddsApiClient.getOdds(
                    leagueRequestName,
                    "eu",
                    "h2h",
                    bookmaker
            );
            matchService.importFromApi(matches, leagueConfig.getName(), leagueConfig.getCountry());
            logger.info("Odds data imported successfully for league {}", leagueId);
        }catch (Exception e){
            logger.error("Importing odds for league {} failed with message {}", leagueId, e.getMessage());
        }
    }

    public void importResultsData(String leagueRequestName){
        try{
            List<MatchResultApiResponse> matches = matchResultsApiClient.getScores(leagueRequestName, "3");
            matchService.importScores(matches);
            logger.info("Scores imported successfully for league {}", leagueRequestName);
        } catch (Exception e){
            logger.error("Importing results for league {} failed with message {}", leagueRequestName, e.getMessage());
        }
        resultService.resolveBets();
    }

}

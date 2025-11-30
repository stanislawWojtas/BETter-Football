package com.sportisfun.backend.scheduler;

import com.sportisfun.backend.services.ImportService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class OddsImportScheduler {

    private final ImportService importService;
    private final static Logger logger = LoggerFactory.getLogger(OddsImportScheduler.class);


    // This method will be called periodically to import odds data for all leagues
    @Scheduled(fixedRateString = "${scheduler.import-odds-rate:86400000}") // Default to 1 day if not specified
    public void fetchTop5LeaguesOdds(){
        logger.info("Importing TOP5 Leagues ODDS");
        importService.importOddsData("epl", "soccer_epl");
        importService.importOddsData("la_liga", "soccer_spain_la_liga");
        importService.importOddsData("bundesliga", "soccer_germany_bundesliga");
        importService.importOddsData("serie_a", "soccer_italy_serie_a");
        importService.importOddsData("ligue_one", "soccer_france_ligue_one");
    }

    @Scheduled(fixedRateString = "${scheduler.import-results-rate:86400000}") //Default 1 day
    public void fetchTop5LeaguesScores(){
        logger.info("Importing TOP5 Leagues SCORES");
        importService.importResultsData("soccer_epl");
        importService.importResultsData("soccer_spain_la_liga");
        importService.importResultsData("soccer_germany_bundesliga");
        importService.importResultsData("soccer_italy_serie_a");
        importService.importResultsData("soccer_france_ligue_one");
    }
}

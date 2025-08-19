package com.sportisfun.backend.scheduler;

import com.sportisfun.backend.services.ImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class OddsImportScheduler {

    private final ImportService importService;

    // This method will be called periodically to import odds data for all leagues
    @Scheduled(fixedRateString = "${scheduler.import-odds-rate:86400000}") // Default to 1 day if not specified
    public void fetchTop5Leagues(){
        System.out.println("\n IMPORTING >>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
        importService.importOddsData("epl", "soccer_epl");
        importService.importOddsData("la_liga", "soccer_spain_la_liga");
        importService.importOddsData("bundesliga", "soccer_germany_bundesliga");
        importService.importOddsData("serie_a", "soccer_italy_serie_a");
        importService.importOddsData("ligue_one", "soccer_france_ligue_one");
    }
}

package com.sportisfun.backend;

import com.sportisfun.backend.services.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(ImportService importService) {
		return args -> {
			// Import odds data for all leagues
			importService.importOddsData("epl", "soccer_epl");
			importService.importOddsData("la_liga", "soccer_spain_la_liga");
			importService.importOddsData("serie_a", "soccer_germany_bundesliga");
			importService.importOddsData("bundesliga", "soccer_italy_serie_a");
			importService.importOddsData("ligue_one", "soccer_france_ligue_one");
		};
	}

}

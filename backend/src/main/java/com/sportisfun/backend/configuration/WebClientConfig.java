package com.sportisfun.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder){
        return builder
                .baseUrl("https://api.the-odds-api.com/v4/sports")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}


package com.sportisfun.backend.services;

import com.sportisfun.backend.DTOs.MatchOddsApiResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OddsApiClient {

    private static final Logger log = LoggerFactory.getLogger(OddsApiClient.class);
    private final WebClient webClient;

    @Value("${api.key}")
    private String apiKey;

    public List<MatchOddsApiResponse> getOdds(String sport, String region, String markets, String bookmakers){

        try{
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/{sport}/odds")
                            .queryParam("region", region)
                            .queryParam("markets", markets)
                            .queryParam("bookmakers", bookmakers)
                            .queryParam("apiKey", apiKey)
                            .build(sport))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<MatchOddsApiResponse>>() {})
                    .block();
        }catch (Exception e){
            System.out.println("Error fetching odds from API: " + e.getMessage());
            return List.of();
        }
    }
}

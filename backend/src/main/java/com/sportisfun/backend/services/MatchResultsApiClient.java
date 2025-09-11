package com.sportisfun.backend.services;

import com.sportisfun.backend.DTOs.MatchResultApiResponse;
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
public class MatchResultsApiClient {

    private static final Logger log = LoggerFactory.getLogger(MatchResultsApiClient.class);
    private final WebClient webClient;

    @Value("${api.key.results}")
    private String apiKey;

    public List<MatchResultApiResponse> getScores(String sport, String daysFrom){

        try{
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/{sport}/scores")
                            .queryParam("daysFrom", daysFrom)
                            .queryParam("apiKey", apiKey)
                            .build(sport))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<MatchResultApiResponse>>(){})
                    .block();
        } catch (Exception e){
            log.error(e.getMessage());
            return List.of();
        }
    }


}

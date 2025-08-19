package com.sportisfun.backend.controllers;

import com.sportisfun.backend.DTOs.MatchOddsDto;
import com.sportisfun.backend.services.MatchOddsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchesOddsController {

    private final MatchOddsService matchOddsService;

    @GetMapping("/upcoming")
    public List<MatchOddsDto> getUpcomingMatches(@RequestParam(required = false) String country) {
        return matchOddsService.getMatchOddsByLeague(country);
    }

    @GetMapping("search")
    public List<MatchOddsDto> searchMatchesByTeam(@RequestParam String teamName){
        return matchOddsService.getMatchesByTeamName(teamName);
    }
}

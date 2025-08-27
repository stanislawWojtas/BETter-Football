package com.sportisfun.backend.services;

import com.sportisfun.backend.DTOs.MatchOddsDto;
import com.sportisfun.backend.models.Match;
import com.sportisfun.backend.repositories.LeagueRepository;
import com.sportisfun.backend.repositories.MatchRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchOddsService {

    private final MatchRepository matchRepository;
    private final LeagueRepository leagueRepository;

    public List<MatchOddsDto> getMatchOddsByLeague(String country) {
        if(country == null){
            List<Match> allMatches = matchRepository.findAllByStartTimeAfterOrderByStartTimeAsc(LocalDateTime.now());
            return mapToMatchOddsDto(allMatches);
        }

        Long leagueId = leagueRepository.findByCountry(country).orElseThrow(
                        () -> new EntityNotFoundException("League not found for: " + country))
                .getId();
        List<Match> matches = matchRepository.findAllByLeagueIdAndStartTimeAfterOrderByStartTimeAsc(leagueId,  LocalDateTime.now());

        return mapToMatchOddsDto(matches);
    }

    public List<MatchOddsDto> getMatchesByTeamName(String teamName) {
        List<Match> allMatches = matchRepository.findAllByStartTimeAfterOrderByStartTimeAsc(LocalDateTime.now());

        List<Match> filteredMatches = allMatches.stream()
                .filter(match ->
                        match.getHomeTeam().getName().toLowerCase().contains(teamName.toLowerCase()) ||
                        match.getAwayTeam().getName().toLowerCase().contains(teamName.toLowerCase()))
                .collect(Collectors.toList());

        return mapToMatchOddsDto(filteredMatches);
    }


    private List<MatchOddsDto> mapToMatchOddsDto(List<Match> matches) {
        return matches.stream()
            .map(match -> MatchOddsDto.builder()
                    .id(match.getId())
                    .homeTeam(match.getHomeTeam().getName())
                    .awayTeam(match.getAwayTeam().getName())
                    .date(match.getStartTime())
                    .league(match.getLeague().getName())
                    .homeWin(match.getOdds().getHomeWin())
                    .awayWin(match.getOdds().getAwayWin())
                    .draw(match.getOdds().getDraw())
                    .build())
            .collect(Collectors.toList());
    }
}
package com.sportisfun.backend.services;

import com.sportisfun.backend.DTOs.MatchOddsApiResponse;
import com.sportisfun.backend.DTOs.OutcomeDto;
import com.sportisfun.backend.models.Match;
import com.sportisfun.backend.models.Odds;
import com.sportisfun.backend.models.Team;
import com.sportisfun.backend.repositories.MatchRepository;
import com.sportisfun.backend.repositories.OddsRepository;
import com.sportisfun.backend.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    private final OddsRepository oddsRepository;

    public void importFromApi(List<MatchOddsApiResponse> matches){
        for(MatchOddsApiResponse dto : matches){
            Team home = getOrCreateTeam(dto.getHomeTeam());
            Team away = getOrCreateTeam(dto.getAwayTeam());

            // Parsing the data
            LocalDateTime startTime = ZonedDateTime.parse(dto.getCommenceTime()).toLocalDateTime();

            // if match already exist
            if(matchRepository.existsById(dto.getId().hashCode())){
                continue;
            }

            Match match = Match.builder()
                    .id((long) dto.getId().hashCode())
                    .homeTeam(home)
                    .awayTeam(away)
                    .startTime(startTime)
                    .finished(false)
                    .build();

            Odds odds = extractOdds(dto);
            match.setOdds(odds);

            // Cascade.ALL so it will also save the odds
            matchRepository.save(match);
        }
    }

    private Team getOrCreateTeam(String name){
        return teamRepository.findByName(name)
                .orElseGet(() -> teamRepository.save(Team.builder().name(name).build()));
    }

    private Odds extractOdds(MatchOddsApiResponse dto){

        var outcomes = dto.getBookmakers().getFirst().getMarkets().getFirst().getOutcomes();

        BigDecimal homeWin = null;
        BigDecimal awayWin = null;
        BigDecimal draw = null;

        for(OutcomeDto outcome : outcomes){
            if (outcome.getName().equals("Draw")){
                draw = new BigDecimal(outcome.getPrice());
            } else if (outcome.getName().equalsIgnoreCase(dto.getHomeTeam())){
                homeWin = new BigDecimal(outcome.getPrice());
            } else if (outcome.getName().equalsIgnoreCase(dto.getAwayTeam())){
                awayWin = new BigDecimal(outcome.getPrice());
            } else{
                throw new RuntimeException("Cannot find the outcome " + outcome.getName() + "when getting the api data");
            }
        }

        return Odds.builder()
                .homeWin(homeWin)
                .awayWin(awayWin)
                .draw(draw)
                .build();
    }
}

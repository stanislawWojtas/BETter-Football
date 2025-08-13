package com.sportisfun.backend.services;

import com.sportisfun.backend.DTOs.MatchOddsApiResponse;
import com.sportisfun.backend.DTOs.OutcomeDto;
import com.sportisfun.backend.models.League;
import com.sportisfun.backend.models.Match;
import com.sportisfun.backend.models.Odds;
import com.sportisfun.backend.models.Team;
import com.sportisfun.backend.repositories.LeagueRepository;
import com.sportisfun.backend.repositories.MatchRepository;
import com.sportisfun.backend.repositories.OddsRepository;
import com.sportisfun.backend.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    private final OddsRepository oddsRepository;
    private final LeagueRepository leagueRepository;

    @Transactional
    public void importFromApi(List<MatchOddsApiResponse> matches, String leagueName, String leagueCountry){
        // skip matches without courses
        matches = matches.stream()
                .filter(match -> !match.getBookmakers().isEmpty())
                .collect(Collectors.toList());

        var league = leagueRepository.findByName(leagueName).orElseGet(() -> leagueRepository.save(League.builder()
                .country(leagueCountry)
                .name(leagueName)
                .build()));

        for(MatchOddsApiResponse dto : matches){

            Odds odds = extractOdds(dto);

            // if match already exist we just update the odds
            if(matchRepository.existsById(dto.getId().hashCode())){
                Match match = matchRepository.findById(dto.getId().hashCode()).orElseThrow();
                updateOdds(match, dto);
                continue;
            }

            Team home = getOrCreateTeam(dto.getHomeTeam());
            if(home.getLeague() == null) home.setLeague(league);
            Team away = getOrCreateTeam(dto.getAwayTeam());
            if(away.getLeague() == null) away.setLeague(league);

            // Parsing the data
            LocalDateTime startTime = ZonedDateTime.parse(dto.getCommenceTime()).toLocalDateTime();




            Match match = Match.builder()
                    .id((long) dto.getId().hashCode())
                    .homeTeam(home)
                    .awayTeam(away)
                    .startTime(startTime)
                    .finished(false)
                    .league(league)
                    .build();


            match.setOdds(odds);
            odds.setMatch(match);

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

    private void updateOdds(Match match, MatchOddsApiResponse dto){

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

        Odds odds = match.getOdds();
        odds.setHomeWin(homeWin);
        odds.setAwayWin(awayWin);
        odds.setDraw(draw);
    }
}

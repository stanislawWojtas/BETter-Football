package com.sportisfun.backend.services;

import com.sportisfun.backend.DTOs.AddPickDto;
import com.sportisfun.backend.DTOs.BetPickDto;
import com.sportisfun.backend.DTOs.BetSlipDto;
import com.sportisfun.backend.DTOs.PlaceBetSlipRequest;
import com.sportisfun.backend.models.*;
import com.sportisfun.backend.repositories.BetPickRepository;
import com.sportisfun.backend.repositories.BetSlipRepository;
import com.sportisfun.backend.repositories.MatchRepository;
import com.sportisfun.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BetSlipService {

    private final UserRepository userRepository;
    private final BetSlipRepository betSlipRepository;
    private final MatchRepository matchRepository;
    private final BetPickRepository betPickRepository;

    @Transactional(readOnly = true)
    public BetSlipDto getDraft(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User not found"));
        BetSlip slip = betSlipRepository.findFirstByUser_IdAndStatus(id, BetSlipStatus.DRAFT).orElse(null);
        if(slip == null){
            return null;
        }
        return mapToBetSlipDto(slip);
    }

    @Transactional
    public BetSlipDto addPick(AddPickDto addPickDto, Long id){
        User user =  userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User not found"));
        BetSlip slip = betSlipRepository.findFirstByUser_IdAndStatus(id, BetSlipStatus.DRAFT)
                .orElseGet(() -> {
                        BetSlip s = BetSlip.builder()
                                    .user(user)
                                    .status(BetSlipStatus.DRAFT)
                                    .totalOdds(BigDecimal.ONE)
                                    .build();
                        return betSlipRepository.save(s);
                        });
        // This may cause problem cuz we cast from long to int later we have to change it!!!
        Match match = matchRepository.findById(Math.toIntExact(addPickDto.getMatchId())).orElseThrow(
                () -> new IllegalArgumentException("Match with id: " + addPickDto.getMatchId() + " does not exist")
        );
        if(match.isFinished()){
            throw new IllegalStateException("Cannot add match that has already ended");
        }
        boolean alreadyInSlip = slip.getBetPicks().stream()
                .anyMatch(p -> p.getMatch().getId().equals(match.getId()));
        if(alreadyInSlip){
            throw new IllegalStateException("Match is already in slip. Remove it first");
        }

        BigDecimal odd = extractOdds(addPickDto.getBetOption(), match.getOdds());
        BetPick pick = BetPick.builder()
                .match(match)
                .result(BetPickResult.PENDING)
                .selectedOdd(odd)
                .betOption(addPickDto.getBetOption())
                .build();

        slip.addPick(pick);
        betSlipRepository.save(slip);
        return mapToBetSlipDto(slip);
    }

    @Transactional
    public BetSlipDto removePick(Long pickId, Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("User not found"));
        BetSlip slip = betSlipRepository.findFirstByUser_IdAndStatus(userId, BetSlipStatus.DRAFT).orElseThrow(() ->
                new IllegalStateException("There is no active bet slip to remove from"));

        BetPick pick = betPickRepository.findById(pickId).orElseThrow(() ->
                new IllegalStateException("Pick with id: " + pickId + " does not exist"));

        if(!slip.getBetPicks().contains(pick)){
            throw new IllegalStateException("Pick with id: " + pickId + " does not exist in current slip");
        }

        slip.removePick(pick);
        betSlipRepository.save(slip);
        return mapToBetSlipDto(slip);
    }

    @Transactional
    public BetSlipDto place(PlaceBetSlipRequest req, Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("User not found"));
        BetSlip slip = betSlipRepository.findFirstByUser_IdAndStatus(userId, BetSlipStatus.DRAFT).orElseThrow(() ->
                new IllegalStateException("There is no active bet slip"));
        if(slip.getBetPicks().isEmpty()){
            throw new IllegalStateException("Bet slip must contain at least one pick");
        }
        slip.place(req.getStake());
        user.setBalance(user.getBalance().subtract(slip.getStake()));
// don't have to use save here cuz its auto
//        betSlipRepository.save(slip);
//        userRepository.save(user);
        return mapToBetSlipDto(slip);
    }

    private BigDecimal extractOdds(BetOption betOption, Odds odds){
        if(odds == null){
            throw new IllegalArgumentException("There are no odds for this match");
        }
        if(betOption.equals(BetOption.AWAY)){
            return odds.getAwayWin();
        } else if (betOption.equals(BetOption.HOME)) {
            return odds.getHomeWin();
        } else {
            return odds.getDraw();
        }
    }


    private BetSlipDto mapToBetSlipDto(BetSlip slip){
        Set<BetPickDto> picks = slip.getBetPicks().stream()
                .map(p -> BetPickDto.builder()
                        .id(p.getId())
                        .matchId(p.getMatch().getId())
                        .option(p.getBetOption())
                        .selectedOdds(p.getSelectedOdd())
                        .result(p.getResult())
                        .build())
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return BetSlipDto.builder()
                .id(slip.getId())
                .stake(slip.getStake())
                .status(slip.getStatus())
                .totalOdds(slip.getTotalOdds())
                .potentialWin(slip.getPotentialWin())
                .placedAt(slip.getPlacedAt())
                .settledAt(slip.getSettledAt())
                .betPicks(picks)
                .build();
    }
}

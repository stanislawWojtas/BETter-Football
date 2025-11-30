package com.sportisfun.backend.services;

import com.sportisfun.backend.models.*;
import com.sportisfun.backend.repositories.BetSlipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final BetSlipRepository betSlipRepository;

    @Transactional
    public void resolveBets(){
        List<BetSlip> pendingSlips = betSlipRepository.findAllByStatus(BetSlipStatus.PLACED);

        for(BetSlip betSlip : pendingSlips){
            boolean allCompleted = true;

            //checking if all matches has ended
            for(BetPick pick : betSlip.getBetPicks()){
                if(pick.getMatch().getHomeGoals() == null ||
                pick.getMatch().getAwayGoals() == null ||
                !pick.getMatch().isFinished()){
                    allCompleted = false;
                    break;
                }
            }
            if(allCompleted){
                for(BetPick pick : betSlip.getBetPicks()){
                    if(pick.getBetOption() == BetOption.AWAY){
                        if(pick.getMatch().getHomeGoals() < pick.getMatch().getAwayGoals()){
                            pick.setResult(BetPickResult.WIN);
                        }else{
                            pick.setResult(BetPickResult.LOSE);
                        }
                    }else if(pick.getBetOption() == BetOption.HOME){
                        if(pick.getMatch().getHomeGoals() > pick.getMatch().getAwayGoals()){
                            pick.setResult(BetPickResult.WIN);
                        }else{
                            pick.setResult(BetPickResult.LOSE);
                        }
                    }else{
                        if(pick.getMatch().getHomeGoals().equals(pick.getMatch().getAwayGoals())){
                            pick.setResult(BetPickResult.WIN);
                        }else{
                            pick.setResult(BetPickResult.LOSE);
                        }
                    }
                }
                betSlip.settle();
                if(betSlip.getStatus().equals(BetSlipStatus.WON)){
                    User user = betSlip.getUser();
                    user.setBalance(user.getBalance().add(betSlip.getPotentialWin()));
                }
                betSlipRepository.save(betSlip);
            }
        }
    }
}

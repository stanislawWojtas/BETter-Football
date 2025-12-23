package com.sportisfun.backend.controllers;

import com.sportisfun.backend.DTOs.AddPickDto;
import com.sportisfun.backend.DTOs.BetSlipDto;
import com.sportisfun.backend.DTOs.PlaceBetSlipRequest;
import com.sportisfun.backend.security.CustomUserDetails;
import com.sportisfun.backend.services.BetSlipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/betslip")
@RequiredArgsConstructor
public class BetSlipController {
    private final BetSlipService betSlipService;

    @GetMapping("/draft")
    public ResponseEntity<BetSlipDto> getDraft(@AuthenticationPrincipal CustomUserDetails userDetails){
        BetSlipDto dto = betSlipService.getDraft(userDetails.getId());
        return dto == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(dto);
    }

    @PostMapping("/picks")
    public BetSlipDto addPick(@RequestBody @Valid AddPickDto dto, @AuthenticationPrincipal CustomUserDetails userDetails){
        return betSlipService.addPick(dto, userDetails.getId());
    }

    @DeleteMapping("/picks/{pickId}")
    public BetSlipDto removePick(@PathVariable Long pickId, @AuthenticationPrincipal CustomUserDetails userDetails){
        return betSlipService.removePick(pickId, userDetails.getId());
    }

    @PostMapping("/place")
    public BetSlipDto place(@RequestBody @Valid PlaceBetSlipRequest req, @AuthenticationPrincipal CustomUserDetails userDetails){
        return betSlipService.place(req, userDetails.getId());
    }

    @GetMapping("/history")
    public List<BetSlipDto> getHistory(@AuthenticationPrincipal CustomUserDetails userDetails){
        return betSlipService.getHistory(userDetails.getId());
    }

    @GetMapping("/history/{userId}")
    public List<BetSlipDto> getHistory(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long userId){
        return betSlipService.getHistory(userId);
    }
}

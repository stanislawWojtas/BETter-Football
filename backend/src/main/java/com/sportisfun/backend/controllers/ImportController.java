package com.sportisfun.backend.controllers;

import com.sportisfun.backend.DTOs.MatchOddsApiResponse;
import com.sportisfun.backend.services.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/import")
public class ImportController {

    private final MatchService matchService;

    public ImportController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping("/matches/epl")
    public ResponseEntity<String> importEplMatches(@RequestBody List<MatchOddsApiResponse> matches){
        matchService.importFromApi(matches);
        return ResponseEntity.ok("Matches imported");
    }

    @GetMapping("test")
    public String test(){
        return "test";
    }
}

package com.sportisfun.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Match {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Team awayTeam;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Positive
    private Integer homeGoals;
    @Positive
    private Integer awayGoals;

    private boolean finished;

    @OneToMany(mappedBy = "match")
    private List<BetPick> betPicks;

    @OneToOne(mappedBy = "match", cascade = CascadeType.ALL)
    private Odds odds;
}

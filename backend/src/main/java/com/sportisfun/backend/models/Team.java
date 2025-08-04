package com.sportisfun.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String logoUrl;

    @ManyToOne
    @JoinColumn(name="league_id")
    private League league;

    @OneToMany(
            mappedBy = "homeTeam"
    )
    private List<Match> homeMatches;

    @OneToMany(
            mappedBy = "awayTeam"
    )
    private List<Match> awayMatches;

}

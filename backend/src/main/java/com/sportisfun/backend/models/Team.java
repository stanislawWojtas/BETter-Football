package com.sportisfun.backend.models;

import jakarta.persistence.*;
import lombok.*;

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

    //@Column(nullable = false)
    private String logoUrl;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
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

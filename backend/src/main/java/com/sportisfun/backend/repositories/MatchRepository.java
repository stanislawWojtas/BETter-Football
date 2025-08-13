package com.sportisfun.backend.repositories;

import com.sportisfun.backend.models.League;
import com.sportisfun.backend.models.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {

    List<Match> findAllByLeague_Id(Long leagueId);
}

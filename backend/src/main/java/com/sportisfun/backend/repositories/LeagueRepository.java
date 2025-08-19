package com.sportisfun.backend.repositories;

import com.sportisfun.backend.models.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {
    public Optional<League> findByName(String name);
    public Optional<League> findByCountry(String country);
}

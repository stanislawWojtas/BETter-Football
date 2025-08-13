package com.sportisfun.backend.repositories;

import com.sportisfun.backend.models.Odds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OddsRepository extends JpaRepository<Odds, Long> {
}

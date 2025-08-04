package com.sportisfun.backend.repositories;

import com.sportisfun.backend.models.BetPick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetPickRepository extends JpaRepository<BetPick, Long> {
}

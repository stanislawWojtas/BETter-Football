package com.sportisfun.backend.repositories;

import com.sportisfun.backend.models.BetSlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetSlipRepository extends JpaRepository<BetSlip, Long> {
}

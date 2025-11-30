package com.sportisfun.backend.repositories;

import com.sportisfun.backend.models.BetSlip;
import com.sportisfun.backend.models.BetSlipStatus;
import com.sportisfun.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BetSlipRepository extends JpaRepository<BetSlip, Long> {
    List<BetSlip> findAllByUserId(Long id);
    Optional<BetSlip> findFirstByUser_IdAndStatus(Long id, BetSlipStatus status);
    List<BetSlip> findAllByStatus(BetSlipStatus status);
}

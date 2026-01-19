package com.sportisfun.backend.repositories;

import com.sportisfun.backend.models.Friendship;
import com.sportisfun.backend.models.FriendshipStatus;
import com.sportisfun.backend.models.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    boolean existsByRequesterIdAndAddresseeId(Long requesterId, Long addresseeId);

    Friendship findByRequesterIdAndAddresseeId(Long requesterId, Long addresseeId, Sort sort);

    Friendship findByRequesterIdAndAddresseeId(Long requesterId, Long addresseeId);

    Optional<Friendship> findByRequesterIdAndAddresseeIdAndStatus(Long requesterId, Long addresseeId, FriendshipStatus status);

    List<Friendship> findAllByAddresseeId(Long addresseeId);

    List<Friendship> findAllByAddresseeIdOrRequesterId(Long addresseeId, Long requesterId);
}

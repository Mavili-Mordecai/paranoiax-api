package com.paranoiax.users.infrastructure.persistence.repositories;

import com.paranoiax.users.domain.models.friendship.FriendshipStatus;
import com.paranoiax.users.infrastructure.persistence.entities.FriendshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaFriendshipRepository extends JpaRepository<FriendshipEntity, UUID> {
    List<FriendshipEntity> findAllByUserId(UUID userId);
    List<FriendshipEntity> findAllByFriendIdAndStatus(UUID friendId, FriendshipStatus status);
    Optional<FriendshipEntity> findAllByUserIdAndFriendId(UUID userId, UUID friendId);
}
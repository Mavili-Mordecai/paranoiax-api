package com.paranoiax.users.infrastructure.persistence.repositories;

import com.paranoiax.users.infrastructure.persistence.entities.FriendshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaFriendshipRepository extends JpaRepository<FriendshipEntity, UUID> {
    List<FriendshipEntity> findAllByUserId(UUID userId);
    Optional<FriendshipEntity> findAllByUserIdAndFriendId(UUID userId, UUID friendId);

    @Query("""
                SELECT fe FROM FriendshipEntity fe
                WHERE fe.updatedAt > :updatedAfter AND fe.status != 'DELETED'
                ORDER BY fe.createdAt DESC
                LIMIT :limit OFFSET :offset
            """
    )
    List<FriendshipEntity> findByUser(
            @Param("userId") UUID userId,
            @Param("updatedAfter") Instant updatedAfter,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset
    );

    @Query("""
            SELECT fe FROM FriendshipEntity fe
            WHERE (fe.userId = :userId AND fe.friendId = :friendId) OR (fe.userId = :friendId AND fe.friendId = :userId)
            """
    )
    List<FriendshipEntity> findBetween(@Param("userId") UUID userId, @Param("friendId") UUID friendId);
}
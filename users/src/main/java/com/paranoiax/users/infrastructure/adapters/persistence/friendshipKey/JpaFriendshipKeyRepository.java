package com.paranoiax.users.infrastructure.adapters.persistence.friendshipKey;

import com.paranoiax.users.infrastructure.entities.FriendshipKeyEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface JpaFriendshipKeyRepository extends JpaRepository<FriendshipKeyEntity, UUID> {
    List<FriendshipKeyEntity> findAllByFriendDeviceId(UUID deviceId, Pageable pageable);

    List<FriendshipKeyEntity> findByFriendshipIdAndFriendDeviceIdIn(UUID friendshipId, Collection<UUID> devices);

    @Modifying
    @Query("DELETE FROM FriendshipKeyEntity fk WHERE fk.friendDeviceId = :deviceId AND fk.id IN :ids")
    int deleteAllBy(@Param("deviceId") UUID deviceId, @Param("ids") Collection<UUID> ids);
}
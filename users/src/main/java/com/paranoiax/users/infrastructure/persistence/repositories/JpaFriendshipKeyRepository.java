package com.paranoiax.users.infrastructure.persistence.repositories;

import com.paranoiax.users.infrastructure.persistence.entities.FriendshipKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaFriendshipKeyRepository extends JpaRepository<FriendshipKeyEntity, UUID> {
    List<FriendshipKeyEntity> findAllByFriendDeviceId(UUID deviceId);
}
package com.paranoiax.chats.infrastructure.adapters.persistence.participantKey;

import com.paranoiax.chats.infrastructure.entities.ParticipantKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaParticipantKeyRepository extends JpaRepository<ParticipantKeyEntity, UUID> {
    List<ParticipantKeyEntity> findAllByDeviceId(UUID deviceId);
    void deleteAllByDeviceId(UUID deviceId);
}

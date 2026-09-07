package com.paranoiax.chats.infrastructure.adapters.persistence.participant;

import com.paranoiax.chats.infrastructure.entities.ParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaParticipantRepository extends JpaRepository<ParticipantEntity, UUID> {
    List<ParticipantEntity> findAllByChatId(UUID chatId);
}

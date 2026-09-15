package com.paranoiax.chats.infrastructure.adapters.persistence.invite;

import com.paranoiax.chats.infrastructure.entities.InviteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaInviteRepository extends JpaRepository<InviteEntity, UUID> {
    List<InviteEntity> findAllByChatId(UUID chatId);
}

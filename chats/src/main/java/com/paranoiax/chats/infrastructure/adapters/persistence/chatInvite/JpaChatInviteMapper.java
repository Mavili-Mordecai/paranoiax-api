package com.paranoiax.chats.infrastructure.adapters.persistence.chatInvite;

import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.chats.domain.models.chatInvite.ChatInvite;
import com.paranoiax.chats.domain.models.chatInvite.ChatInviteId;
import com.paranoiax.chats.domain.models.chatInvite.ChatInviteMaxUses;
import com.paranoiax.chats.domain.models.chatInvite.ChatInviteUsesCount;
import com.paranoiax.chats.infrastructure.entities.ChatInviteEntity;
import com.paranoiax.core.domain.users.UserId;
import org.springframework.stereotype.Component;

@Component
public class JpaChatInviteMapper {
    public ChatInvite toDomain(ChatInviteEntity entity) {
        return ChatInvite.of(
                new ChatInviteId(entity.getId()),
                new ChatId(entity.getChatId()),
                new UserId(entity.getCreatedById()),
                new ChatInviteUsesCount(entity.getUsesCount()),
                entity.getMaxUses() != null ? new ChatInviteMaxUses(entity.getMaxUses()) : null,
                entity.getExpiresAt()
        );
    }

    public ChatInviteEntity toEntity(ChatInvite domain) {
        return ChatInviteEntity.builder()
                .id(domain.getId().value())
                .chatId(domain.getChatId().value())
                .createdById(domain.getCreatedById().value())
                .usesCount(domain.getUsesCount().value())
                .maxUses(domain.getMaxUses() != null ? domain.getMaxUses().value() : null)
                .expiresAt(domain.getExpiresAt())
                .build();
    }
}

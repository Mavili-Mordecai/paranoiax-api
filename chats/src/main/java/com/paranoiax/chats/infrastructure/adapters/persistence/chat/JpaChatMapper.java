package com.paranoiax.chats.infrastructure.adapters.persistence.chat;

import com.paranoiax.chats.domain.models.chat.Chat;
import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.chats.infrastructure.entities.ChatEntity;
import com.paranoiax.core.domain.EventsSeq;
import com.paranoiax.core_infra.operationResultMapper.OperationResultsMapper;
import org.springframework.stereotype.Component;

@Component
public class JpaChatMapper implements OperationResultsMapper<Chat, ChatEntity> {

    @Override
    public Class<Chat> getDomainClass() {
        return Chat.class;
    }

    @Override
    public Class<ChatEntity> getEntityClass() {
        return ChatEntity.class;
    }

    @Override
    public Chat toDomain(ChatEntity entity) {
        return Chat.of(
                new ChatId(entity.getId()),
                entity.getType(),
                new EventsSeq(entity.getEventsSeq()),
                entity.getLastActivityAt(),
                entity.getCreatedAt()
        );
    }

    @Override
    public ChatEntity toEntity(Chat domain) {
        return ChatEntity.builder()
                .id(domain.getId().value())
                .type(domain.getType())
                .eventsSeq(domain.getEventsSeq().value())
                .lastActivityAt(domain.getLastActivityAt())
                .createdAt(domain.getCreatedAt())
                .build();
    }
}

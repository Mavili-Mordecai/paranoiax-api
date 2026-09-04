package com.paranoiax.chats.infrastructure.adapters.persistence.participant;

import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.chats.domain.models.participant.Participant;
import com.paranoiax.chats.domain.models.participant.ParticipantId;
import com.paranoiax.chats.infrastructure.entities.ParticipantEntity;
import com.paranoiax.core.domain.EventsSeq;
import com.paranoiax.core.domain.users.UserId;
import org.springframework.stereotype.Component;

@Component
public class JpaParticipantMapper {
    public Participant toDomain(ParticipantEntity entity) {
        return Participant.of(
                new ParticipantId(entity.getId()),
                new ChatId(entity.getChatId()),
                new UserId(entity.getUserId()),
                entity.getRole(),
                entity.isMuted(),
                entity.isPinned(),
                new EventsSeq(entity.getLastReadSeq()),
                entity.getPermissions(),
                entity.getUpdatedAt(),
                entity.getJoinedAt()
        );
    }

    public ParticipantEntity toEntity(Participant domain) {
        return ParticipantEntity.builder()
                .id(domain.getId().value())
                .chatId(domain.getChatId().value())
                .userId(domain.getUserId().value())
                .role(domain.getRole())
                .isMuted(domain.isMuted())
                .isPinned(domain.isPinned())
                .lastReadSeq(domain.getLastReadSeq().value())
                .permissions(domain.getPermissions())
                .updatedAt(domain.getUpdatedAt())
                .joinedAt(domain.getJoinedAt())
                .build();
    }
}

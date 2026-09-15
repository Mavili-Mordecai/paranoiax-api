package com.paranoiax.chats.infrastructure.adapters.persistence.invite;

import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.chats.domain.models.invite.Invite;
import com.paranoiax.chats.domain.models.invite.InviteId;
import com.paranoiax.chats.domain.models.invite.InviteMaxUses;
import com.paranoiax.chats.domain.models.invite.InviteUsesCount;
import com.paranoiax.chats.infrastructure.entities.InviteEntity;
import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.core_infra.operationResultMapper.OperationResultsMapper;
import org.springframework.stereotype.Component;

@Component
public class JpaInviteMapper implements OperationResultsMapper<Invite, InviteEntity> {

    @Override
    public Class<Invite> getDomainClass() {
        return Invite.class;
    }

    @Override
    public Class<InviteEntity> getEntityClass() {
        return InviteEntity.class;
    }

    public Invite toDomain(InviteEntity entity) {
        return Invite.of(
                new InviteId(entity.getId()),
                new ChatId(entity.getChatId()),
                new UserId(entity.getCreatedById()),
                new InviteUsesCount(entity.getUsesCount()),
                entity.getMaxUses() != null ? new InviteMaxUses(entity.getMaxUses()) : null,
                entity.getExpiresAt()
        );
    }

    public InviteEntity toEntity(Invite domain) {
        return InviteEntity.builder()
                .id(domain.getId().value())
                .chatId(domain.getChatId().value())
                .createdById(domain.getCreatedById().value())
                .usesCount(domain.getUsesCount().value())
                .maxUses(domain.getMaxUses() != null ? domain.getMaxUses().value() : null)
                .expiresAt(domain.getExpiresAt())
                .build();
    }
}

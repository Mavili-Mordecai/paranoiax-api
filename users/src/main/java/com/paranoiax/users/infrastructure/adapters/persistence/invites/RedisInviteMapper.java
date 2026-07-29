package com.paranoiax.users.infrastructure.adapters.persistence.invites;

import com.paranoiax.users.domain.models.invite.Invite;
import com.paranoiax.users.infrastructure.common.operationResultMapper.OperationResultsMapper;
import org.springframework.stereotype.Component;

@Component
public class RedisInviteMapper implements OperationResultsMapper<Invite, RedisInviteDto> {
    @Override
    public Class<Invite> getDomainClass() {
        return Invite.class;
    }

    @Override
    public Class<RedisInviteDto> getEntityClass() {
        return RedisInviteDto.class;
    }

    @Override
    public RedisInviteDto toEntity(Invite invite) {
        return new RedisInviteDto(
                invite.getUserId(),
                invite.getRegistrationToken(),
                invite.getCreatedAt(),
                invite.getExpiresAt()
        );
    }

    @Override
    public Invite toDomain(RedisInviteDto redisInviteDto) {
        return new Invite(
                redisInviteDto.getUserId(),
                redisInviteDto.getRegistrationToken(),
                redisInviteDto.getCreatedAt(),
                redisInviteDto.getExpiresAt()
        );
    }
}

package com.paranoiax.users.infrastructure.adapters.persistence.friendship;

import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.core_infra.operationResultMapper.OperationResultsMapper;
import com.paranoiax.users.domain.models.friendship.Friendship;
import com.paranoiax.users.domain.models.friendship.FriendshipAttributes;
import com.paranoiax.users.domain.models.friendship.FriendshipId;
import org.springframework.stereotype.Component;

@Component
public class RedisFriendshipMapper implements OperationResultsMapper<Friendship, RedisFriendshipDto> {

    @Override
    public Class<Friendship> getDomainClass() {
        return Friendship.class;
    }

    @Override
    public Class<RedisFriendshipDto> getEntityClass() {
        return RedisFriendshipDto.class;
    }

    @Override
    public RedisFriendshipDto toEntity(Friendship friendship) {
        return new RedisFriendshipDto(
                friendship.getId().value(),
                friendship.getUserId().value(),
                friendship.getFriendId().value(),
                friendship.getStatus(),
                friendship.getAttributes() != null ? friendship.getAttributes().data() : null,
                friendship.getUpdatedAt(),
                friendship.getCreatedAt()
        );
    }

    @Override
    public Friendship toDomain(RedisFriendshipDto dto) {
        return Friendship.of(
                new FriendshipId(dto.getId()),
                new UserId(dto.getUserId()),
                new UserId(dto.getFriendId()),
                dto.getStatus(),
                dto.getAttributes() != null ? new FriendshipAttributes(dto.getAttributes()) : null,
                dto.getUpdatedAt(),
                dto.getCreatedAt()
        );
    }
}
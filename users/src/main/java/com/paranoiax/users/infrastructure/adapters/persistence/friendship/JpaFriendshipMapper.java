package com.paranoiax.users.infrastructure.adapters.persistence.friendship;

import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.domain.models.friendship.Friendship;
import com.paranoiax.users.domain.models.friendship.FriendshipAttributes;
import com.paranoiax.users.domain.models.friendship.FriendshipId;
import com.paranoiax.users.infrastructure.common.operationResultMapper.OperationResultsMapper;
import com.paranoiax.users.infrastructure.persistence.entities.FriendshipEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaFriendshipMapper implements OperationResultsMapper<Friendship, FriendshipEntity> {

    @Override
    public Class<Friendship> getDomainClass() {
        return Friendship.class;
    }

    @Override
    public Class<FriendshipEntity> getEntityClass() {
        return FriendshipEntity.class;
    }

    @Override
    public FriendshipEntity toEntity(Friendship domain) {
        return FriendshipEntity.builder()
                .id(domain.getId().value())
                .userId(domain.getUserId().value())
                .friendId(domain.getFriendId().value())
                .status(domain.getStatus())
                .attributes(domain.getAttributes().data())
                .createdAt(domain.getCreatedAt())
                .build();
    }

    @Override
    public Friendship toDomain(FriendshipEntity entity) {
        return Friendship.of(
                new FriendshipId(entity.getId()),
                new UserId(entity.getUserId()),
                new UserId(entity.getFriendId()),
                entity.getStatus(),
                new FriendshipAttributes(entity.getAttributes()),
                entity.getCreatedAt()
        );
    }
}
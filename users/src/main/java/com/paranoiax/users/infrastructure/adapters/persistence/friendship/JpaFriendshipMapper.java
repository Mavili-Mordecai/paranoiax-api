package com.paranoiax.users.infrastructure.adapters.persistence.friendship;

import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.domain.models.friendship.Friendship;
import com.paranoiax.users.domain.models.friendship.FriendshipAttributes;
import com.paranoiax.users.domain.models.friendship.FriendshipId;
import com.paranoiax.users.infrastructure.entities.FriendshipEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaFriendshipMapper {

    public FriendshipEntity toEntity(Friendship domain) {
        return FriendshipEntity.builder()
                .id(domain.getId().value())
                .userId(domain.getUserId().value())
                .friendId(domain.getFriendId().value())
                .status(domain.getStatus())
                .attributes(domain.getAttributes() != null ? domain.getAttributes().data() : null)
                .updatedAt(domain.getUpdatedAt())
                .createdAt(domain.getCreatedAt())
                .build();
    }

    public Friendship toDomain(FriendshipEntity entity) {
        return Friendship.of(
                new FriendshipId(entity.getId()),
                new UserId(entity.getUserId()),
                new UserId(entity.getFriendId()),
                entity.getStatus(),
                entity.getAttributes() != null ? new FriendshipAttributes(entity.getAttributes()) : null,
                entity.getUpdatedAt(),
                entity.getCreatedAt()
        );
    }
}
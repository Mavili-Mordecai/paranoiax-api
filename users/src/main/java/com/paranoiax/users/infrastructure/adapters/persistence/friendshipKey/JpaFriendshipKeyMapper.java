package com.paranoiax.users.infrastructure.adapters.persistence.friendshipKey;

import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.users.domain.models.friendship.FriendshipId;
import com.paranoiax.users.domain.models.friendship.key.FriendshipKey;
import com.paranoiax.users.domain.models.friendship.key.FriendshipKeyId;
import com.paranoiax.users.domain.models.friendship.key.FriendshipSharedKey;
import com.paranoiax.users.infrastructure.common.operationResultMapper.OperationResultsMapper;
import com.paranoiax.users.infrastructure.persistence.entities.FriendshipKeyEntity;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class JpaFriendshipKeyMapper implements OperationResultsMapper<FriendshipKey, FriendshipKeyEntity> {

    @Override
    public Class<FriendshipKey> getDomainClass() {
        return FriendshipKey.class;
    }

    @Override
    public Class<FriendshipKeyEntity> getEntityClass() {
        return FriendshipKeyEntity.class;
    }

    @Override
    public FriendshipKeyEntity toEntity(FriendshipKey domain) {
        return FriendshipKeyEntity.builder()
                .id(domain.getId().value())
                .friendshipId(domain.getFriendshipId().value())
                .friendDeviceId(domain.getFriendDeviceId().value())
                .sharedKey(domain.getSharedKey().data())
                .createdAt(domain.getCreatedAt())
                .build();
    }

    @Override
    public FriendshipKey toDomain(FriendshipKeyEntity entity) {
        return FriendshipKey.of(
                new FriendshipKeyId(entity.getId()),
                new FriendshipId(entity.getFriendshipId()),
                new DeviceId(entity.getFriendDeviceId()),
                new FriendshipSharedKey(entity.getSharedKey()),
                entity.getCreatedAt()
        );
    }

    public List<FriendshipKeyEntity> toEntityList(Collection<FriendshipKey> domainList) {
        return domainList.stream().map(this::toEntity).toList();
    }

    public List<FriendshipKey> toDomainList(Collection<FriendshipKeyEntity> entityList) {
        return entityList.stream().map(this::toDomain).toList();
    }
}
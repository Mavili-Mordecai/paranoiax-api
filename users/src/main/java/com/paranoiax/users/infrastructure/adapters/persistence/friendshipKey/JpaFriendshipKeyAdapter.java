package com.paranoiax.users.infrastructure.adapters.persistence.friendshipKey;

import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.users.application.ports.out.FriendshipKeyPort;
import com.paranoiax.users.domain.models.friendship.FriendshipId;
import com.paranoiax.users.domain.models.friendship.key.FriendshipKey;
import com.paranoiax.users.domain.models.friendship.key.FriendshipKeyId;
import com.paranoiax.users.infrastructure.entities.FriendshipKeyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaFriendshipKeyAdapter implements FriendshipKeyPort {
    private final JpaFriendshipKeyRepository repository;
    private final JpaFriendshipKeyMapper mapper;

    @Override
    public List<FriendshipKey> insertAll(Collection<FriendshipKey> keys) {
        List<FriendshipKeyEntity> entities = mapper.toEntityList(keys);
        entities.forEach(entity -> entity.setNew(false));
        return mapper.toDomainList(repository.saveAll(entities));
    }

    @Override
    public List<FriendshipKey> saveAll(Collection<FriendshipKey> keys) {
        return mapper.toDomainList(repository.saveAll(mapper.toEntityList(keys)));
    }

    @Override
    public List<FriendshipKey> findAllByDevice(DeviceId deviceId, Integer limit, Integer offset) {
        return mapper.toDomainList(repository.findAllByFriendDeviceId(deviceId.value(), PageRequest.of(offset, limit)));
    }

    @Override
    public List<FriendshipKey> findExistingKeys(FriendshipId friendshipId, Collection<DeviceId> devices) {
        return mapper.toDomainList(repository.findByFriendshipIdAndFriendDeviceIdIn(friendshipId.value(), devices.stream().map(DeviceId::value).toList()));
    }

    @Override
    @Transactional
    public int deleteAllBy(DeviceId deviceId, Collection<FriendshipKeyId> ids) {
        return repository.deleteAllBy(deviceId.value(), ids.stream().map(FriendshipKeyId::value).toList());
    }
}
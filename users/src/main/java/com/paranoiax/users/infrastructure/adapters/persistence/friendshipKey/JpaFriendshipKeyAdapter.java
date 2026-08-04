package com.paranoiax.users.infrastructure.adapters.persistence.friendshipKey;

import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.users.application.ports.out.FriendshipKeyPort;
import com.paranoiax.users.domain.models.friendship.key.FriendshipKey;
import com.paranoiax.users.domain.models.friendship.key.FriendshipKeyId;
import com.paranoiax.users.infrastructure.persistence.repositories.JpaFriendshipKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaFriendshipKeyAdapter implements FriendshipKeyPort {
    private final JpaFriendshipKeyRepository repository;
    private final JpaFriendshipKeyMapper mapper;

    @Override
    public List<FriendshipKey> saveAll(Collection<FriendshipKey> keys) {
        return mapper.toDomainList(repository.saveAll(mapper.toEntityList(keys)));
    }

    @Override
    public List<FriendshipKey> findAllByDeviceId(DeviceId deviceId) {
        return mapper.toDomainList(repository.findAllByFriendDeviceId(deviceId.value()));
    }

    @Override
    public void deleteAllById(Collection<FriendshipKeyId> ids) {
        repository.deleteAllById(ids.stream().map(FriendshipKeyId::value).toList());
    }
}
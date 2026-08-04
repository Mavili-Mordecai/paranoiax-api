package com.paranoiax.users.application.ports.out;

import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.users.domain.models.friendship.key.FriendshipKey;
import com.paranoiax.users.domain.models.friendship.key.FriendshipKeyId;

import java.util.Collection;
import java.util.List;

public interface FriendshipKeyPort {
    List<FriendshipKey> saveAll(Collection<FriendshipKey> keys);
    List<FriendshipKey> findAllByDeviceId(DeviceId deviceId);
    void deleteAllById(Collection<FriendshipKeyId> ids);
}
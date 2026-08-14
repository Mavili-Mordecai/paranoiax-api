package com.paranoiax.users.application.ports.out;

import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.users.domain.models.friendship.FriendshipId;
import com.paranoiax.users.domain.models.friendship.key.FriendshipKey;
import com.paranoiax.users.domain.models.friendship.key.FriendshipKeyId;

import java.util.Collection;
import java.util.List;

public interface FriendshipKeyPort {
    List<FriendshipKey> insertAll(Collection<FriendshipKey> keys);
    List<FriendshipKey> saveAll(Collection<FriendshipKey> keys);
    List<FriendshipKey> findAllByDevice(DeviceId deviceId, Integer limit, Integer offset);
    List<FriendshipKey> findExistingKeys(FriendshipId friendshipId, Collection<DeviceId> devices);
    int deleteAllBy(DeviceId deviceId, Collection<FriendshipKeyId> ids);
}
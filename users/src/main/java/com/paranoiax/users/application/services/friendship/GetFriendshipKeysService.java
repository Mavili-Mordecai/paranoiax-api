package com.paranoiax.users.application.services.friendship;

import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.users.application.ports.in.friendship.getKeys.FriendshipKeyResult;
import com.paranoiax.users.application.ports.in.friendship.getKeys.GetFriendshipKeysQuery;
import com.paranoiax.users.application.ports.in.friendship.getKeys.GetFriendshipKeysUseCase;
import com.paranoiax.users.application.ports.out.FriendshipKeyPort;
import com.paranoiax.users.domain.models.friendship.key.FriendshipKey;

import java.time.Instant;
import java.util.List;

public class GetFriendshipKeysService implements GetFriendshipKeysUseCase {
    private final FriendshipKeyPort friendshipKeyPort;

    public GetFriendshipKeysService(FriendshipKeyPort friendshipKeyPort) {
        this.friendshipKeyPort = friendshipKeyPort;
    }

    @Override
    public FriendshipKeyResult execute(GetFriendshipKeysQuery query) {
        List<FriendshipKey> keys = friendshipKeyPort.findAllByDevice(
                new DeviceId(query.deviceId()),
                query.limit(),
                query.offset()
        );

        return new FriendshipKeyResult(
                keys,
                keys.size() == query.limit(),
                Instant.now().toEpochMilli()
        );
    }
}
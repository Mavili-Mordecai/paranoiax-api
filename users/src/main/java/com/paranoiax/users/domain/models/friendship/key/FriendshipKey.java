package com.paranoiax.users.domain.models.friendship.key;

import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.users.domain.models.friendship.FriendshipId;

import java.time.Instant;

public class FriendshipKey {
    private final FriendshipKeyId id;
    private final FriendshipId friendshipId;
    private final DeviceId friendDeviceId;
    private FriendshipSharedKey sharedKey;
    private final Instant createdAt;

    public FriendshipKey(
            FriendshipKeyId id, FriendshipId friendshipId, DeviceId friendDeviceId, FriendshipSharedKey sharedKey,
            Instant createdAt
    ) {
        this.id = id;
        this.friendshipId = friendshipId;
        this.friendDeviceId = friendDeviceId;
        this.sharedKey = sharedKey;
        this.createdAt = createdAt;
    }

    public static FriendshipKey create(
            FriendshipId friendshipId, DeviceId friendDeviceId, FriendshipSharedKey sharedKey
    ) {
        return new FriendshipKey(FriendshipKeyId.create(), friendshipId, friendDeviceId, sharedKey, Instant.now());
    }

    public static FriendshipKey of(
            FriendshipKeyId id, FriendshipId friendshipId, DeviceId friendDeviceId, FriendshipSharedKey sharedKey,
            Instant createdAt
    ) {
        return new FriendshipKey(id, friendshipId, friendDeviceId, sharedKey, createdAt);
    }

    public void changeSharedKey(FriendshipSharedKey sharedKey) {
        this.sharedKey = sharedKey;
    }

    public boolean hasSameKey(FriendshipSharedKey key) {
        return this.sharedKey.equals(key);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public FriendshipSharedKey getSharedKey() {
        return sharedKey;
    }

    public DeviceId getFriendDeviceId() {
        return friendDeviceId;
    }

    public FriendshipId getFriendshipId() {
        return friendshipId;
    }

    public FriendshipKeyId getId() {
        return id;
    }
}
package com.paranoiax.core.application;

import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.core.domain.users.UserId;

import java.time.Instant;
import java.util.UUID;

public class RefreshToken extends AuthToken {
    private final UUID id;

    public RefreshToken(UUID id, UserId userId, DeviceId deviceId, Instant expiresAt) {
        super(userId, deviceId, expiresAt);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}

package com.paranoiax.users.application.ports.in.auth;

import com.paranoiax.users.domain.models.device.DeviceId;
import com.paranoiax.users.domain.models.user.UserId;

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

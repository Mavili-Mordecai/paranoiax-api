package com.paranoiax.core.application;

import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.core.domain.users.UserId;

import java.time.Duration;
import java.time.Instant;

public abstract class AuthToken {
    private final UserId userId;
    private final DeviceId deviceId;
    private final Instant expiresAt;

    protected AuthToken(UserId userId, DeviceId deviceId, Instant expiresAt) {
        this.userId = userId;
        this.deviceId = deviceId;
        this.expiresAt = expiresAt;
    }

    public Duration getRemainingTtl() {
        return Duration.between(Instant.now(), expiresAt);
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public DeviceId getDeviceId() {
        return deviceId;
    }

    public UserId getUserId() {
        return userId;
    }
}
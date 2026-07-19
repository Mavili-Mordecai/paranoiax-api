package com.paranoiax.users.domain.models.challenge;

import com.paranoiax.users.domain.Require;
import com.paranoiax.users.domain.exceptions.DomainErrorCode;
import com.paranoiax.users.domain.models.device.DeviceId;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class AuthChallenge {
    private final DeviceId deviceId;
    private final Challenge challenge;
    private final Instant createdAt;
    private final Instant expiresAt;

    public AuthChallenge(DeviceId deviceId, Challenge challenge, Instant createdAt, Instant expiresAt) {
        this.deviceId = Require.notNull(deviceId, DomainErrorCode.MISSING_REQUIRED_FIELD, "deviceId");
        this.challenge = Require.notNull(challenge, DomainErrorCode.MISSING_REQUIRED_FIELD, "challenge");
        this.createdAt = Require.notNull(createdAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "createdAt");
        this.expiresAt = Require.notNull(expiresAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "expiresAt");
    }

    public static AuthChallenge create(DeviceId deviceId, Duration ttl) {
        Instant now = Instant.now();

        return new AuthChallenge(
                deviceId,
                new Challenge(UUID.randomUUID().toString()),
                now,
                now.plus(ttl)
        );
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public DeviceId getDeviceId() {
        return deviceId;
    }
}

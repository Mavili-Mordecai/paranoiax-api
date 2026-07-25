package com.paranoiax.users.domain.models.challenge;

import com.paranoiax.users.domain.Require;
import com.paranoiax.users.domain.exceptions.DomainErrorCode;
import com.paranoiax.users.domain.models.device.DeviceId;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class Challenge {
    private final DeviceId deviceId;
    private final ChallengeValue challenge;
    private final Instant createdAt;
    private final Instant expiresAt;

    public Challenge(DeviceId deviceId, ChallengeValue challenge, Instant createdAt, Instant expiresAt) {
        this.deviceId = Require.notNull(deviceId, DomainErrorCode.MISSING_REQUIRED_FIELD, "deviceId");
        this.challenge = Require.notNull(challenge, DomainErrorCode.MISSING_REQUIRED_FIELD, "challenge");
        this.createdAt = Require.notNull(createdAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "createdAt");
        this.expiresAt = Require.notNull(expiresAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "expiresAt");
    }

    public static Challenge create(DeviceId deviceId, ChallengeValue value, Duration ttl) {
        Instant now = Instant.now();
        return new Challenge(
                deviceId,
                value,
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

    public ChallengeValue getChallenge() {
        return challenge;
    }

    public DeviceId getDeviceId() {
        return deviceId;
    }
}
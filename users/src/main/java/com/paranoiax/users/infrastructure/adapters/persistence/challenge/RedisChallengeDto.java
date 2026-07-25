package com.paranoiax.users.infrastructure.adapters.persistence.challenge;

import java.time.Instant;
import java.util.UUID;

public class RedisChallengeDto {
    private UUID deviceId;
    private String challenge;
    private Instant createdAt;
    private Instant expiresAt;

    public RedisChallengeDto() {
    }

    public RedisChallengeDto(UUID deviceId, String challenge, Instant createdAt, Instant expiresAt) {
        this.deviceId = deviceId;
        this.challenge = challenge;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }
}
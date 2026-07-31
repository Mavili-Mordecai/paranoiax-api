package com.paranoiax.users.infrastructure.adapters.persistence.deviceMigration;

import java.time.Instant;
import java.util.UUID;

public class RedisDeviceMigrationDto {
    private UUID id;
    private UUID deviceId;
    private UUID userId;
    private UUID blobId;
    private String challenge;
    private String status;
    private String identityKey;
    private String encryptionKey;
    private String deviceSignature;
    private Instant createdAt;
    private Instant expiresAt;

    public RedisDeviceMigrationDto() {
    }

    public RedisDeviceMigrationDto(UUID id, UUID deviceId, UUID userId, UUID blobId, String challenge, String status, String identityKey, String encryptionKey, String deviceSignature, Instant createdAt, Instant expiresAt) {
        this.id = id;
        this.deviceId = deviceId;
        this.userId = userId;
        this.blobId = blobId;
        this.challenge = challenge;
        this.status = status;
        this.identityKey = identityKey;
        this.encryptionKey = encryptionKey;
        this.deviceSignature = deviceSignature;
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

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public String getIdentityKey() {
        return identityKey;
    }

    public void setIdentityKey(String identityKey) {
        this.identityKey = identityKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public UUID getBlobId() {
        return blobId;
    }

    public void setBlobId(UUID blobId) {
        this.blobId = blobId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDeviceSignature() {
        return deviceSignature;
    }

    public void setDeviceSignature(String deviceSignature) {
        this.deviceSignature = deviceSignature;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }
}
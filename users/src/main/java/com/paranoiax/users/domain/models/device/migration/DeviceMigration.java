package com.paranoiax.users.domain.models.device.migration;

import com.paranoiax.users.domain.Require;
import com.paranoiax.users.domain.exceptions.DomainErrorCode;
import com.paranoiax.users.domain.exceptions.ExpiredException;
import com.paranoiax.users.domain.models.ChallengeValue;
import com.paranoiax.users.domain.models.EncryptionKey;
import com.paranoiax.users.domain.models.IdentityKey;
import com.paranoiax.users.domain.models.device.DeviceId;
import com.paranoiax.users.domain.models.device.DeviceSignature;
import com.paranoiax.users.domain.models.user.UserId;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class DeviceMigration {
    private final DeviceMigrationId id;
    /** The new device's id */
    private final DeviceId deviceId;
    private final UserId userId;
    private final UUID blobId;
    private ChallengeValue challenge;
    private DeviceMigrationStatus status;
    /** The new device's identity key */
    private final IdentityKey identityKey;
    /** The new device's encryption key */
    private final EncryptionKey encryptionKey;
    private final DeviceSignature deviceSignature;
    private final Instant createdAt;
    private final Instant expiresAt;

    private DeviceMigration(
            DeviceMigrationId id, DeviceId deviceId, UserId userId,
            UUID blobId, ChallengeValue challenge, DeviceMigrationStatus status,
            IdentityKey identityKey, EncryptionKey encryptionKey, DeviceSignature deviceSignature,
            Instant createdAt, Instant expiresAt
    ) {
        this.id = Require.notNull(id, DomainErrorCode.MISSING_REQUIRED_FIELD, "id");
        this.deviceId = Require.notNull(deviceId, DomainErrorCode.MISSING_REQUIRED_FIELD, "deviceId");
        this.userId = Require.notNull(userId, DomainErrorCode.MISSING_REQUIRED_FIELD, "userId");
        this.blobId = Require.notNull(blobId, DomainErrorCode.MISSING_REQUIRED_FIELD, "blobId");
        this.status = Require.notNull(status, DomainErrorCode.MISSING_REQUIRED_FIELD, "status");
        this.identityKey = Require.notNull(identityKey, DomainErrorCode.MISSING_REQUIRED_FIELD, "identityKey");
        this.encryptionKey = Require.notNull(encryptionKey, DomainErrorCode.MISSING_REQUIRED_FIELD, "encryptionKey");
        this.deviceSignature = Require.notNull(deviceSignature, DomainErrorCode.MISSING_REQUIRED_FIELD, "deviceSignature");
        this.createdAt = Require.notNull(createdAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "createdAt");
        this.expiresAt = Require.notNull(expiresAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "expiresAt");
        this.challenge = challenge;
    }

    public static DeviceMigration of(
            DeviceMigrationId id, DeviceId deviceId, UserId userId,
            UUID blobId, ChallengeValue challenge, DeviceMigrationStatus status,
            IdentityKey identityKey, EncryptionKey encryptionKey, DeviceSignature deviceSignature,
            Instant createdAt, Instant expiresAt
    ) {
        return new DeviceMigration(
                id,
                deviceId,
                userId,
                blobId,
                challenge,
                status,
                identityKey,
                encryptionKey,
                deviceSignature,
                createdAt,
                expiresAt
        );
    }

    public static DeviceMigration create(
            DeviceMigrationId id, DeviceId deviceId, UserId userId,
            UUID blobId,
            IdentityKey identityKey, EncryptionKey encryptionKey, DeviceSignature deviceSignature,
            Duration ttl
    ) {
        Instant now = Instant.now();
        return new DeviceMigration(
                id,
                deviceId,
                userId,
                blobId,
                null,
                DeviceMigrationStatus.WAITING_FOR_UPLOAD,
                identityKey,
                encryptionKey,
                deviceSignature,
                now,
                now.plus(ttl)
        );
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    public void checkNotExpired() {
        if (isExpired()) {
            throw new ExpiredException("Device migration");
        }
    }

    public Duration getRemainingTtl() {
        return Duration.between(Instant.now(), expiresAt);
    }

    public void confirmUpload(ChallengeValue challenge) {
        if (this.status != DeviceMigrationStatus.WAITING_FOR_UPLOAD) {
            throw new IllegalStateException("Device migration is not waiting for upload");
        }
        this.status = DeviceMigrationStatus.READY_FOR_AUTH;
        this.challenge = Require.notNull(challenge, DomainErrorCode.MISSING_REQUIRED_FIELD, "challenge");
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public IdentityKey getIdentityKey() {
        return identityKey;
    }

    public EncryptionKey getEncryptionKey() {
        return encryptionKey;
    }

    public DeviceSignature getDeviceSignature() {
        return deviceSignature;
    }

    public UUID getBlobId() {
        return blobId;
    }

    public DeviceMigrationStatus getStatus() {
        return status;
    }

    public ChallengeValue getChallenge() {
        return challenge;
    }

    public UserId getUserId() {
        return userId;
    }

    public DeviceId getDeviceId() {
        return deviceId;
    }

    public DeviceMigrationId getId() {
        return id;
    }
}
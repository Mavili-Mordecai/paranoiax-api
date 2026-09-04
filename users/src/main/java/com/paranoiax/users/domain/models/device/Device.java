package com.paranoiax.users.domain.models.device;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.core.domain.devices.DeviceType;
import com.paranoiax.core.domain.exceptions.AlreadyRevokedException;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;
import com.paranoiax.core.domain.exceptions.RevokedException;
import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.core.domain.ActivityTrackable;
import com.paranoiax.core.domain.EncryptionKey;
import com.paranoiax.core.domain.IdentityKey;

import java.time.Instant;

public class Device implements ActivityTrackable {
    private final DeviceId id;
    private final UserId userId;
    private final DeviceName name;
    private final DeviceType type;
    private final IdentityKey identityKey;
    private final EncryptionKey encryptionKey;
    private final DeviceSignature deviceSignature;
    private Instant revokedAt;
    private Instant lastActivityAt;
    private final Instant createdAt;

    private Device(
            DeviceId id, UserId userId,
            DeviceName name, DeviceType type,
            IdentityKey identityKey, EncryptionKey encryptionKey, DeviceSignature deviceSignature,
            Instant revokedAt, Instant lastActivityAt, Instant createdAt
    ) {
        this.id = Require.notNull(id, DomainErrorCode.MISSING_REQUIRED_FIELD, "Id");
        this.userId = Require.notNull(userId, DomainErrorCode.MISSING_REQUIRED_FIELD, "User id");
        this.name = Require.notNull(name, DomainErrorCode.MISSING_REQUIRED_FIELD, "Name");
        this.type = Require.notNull(type, DomainErrorCode.MISSING_REQUIRED_FIELD, "Type");
        this.identityKey = Require.notNull(identityKey, DomainErrorCode.MISSING_REQUIRED_FIELD, "Identity key");
        this.encryptionKey = Require.notNull(encryptionKey, DomainErrorCode.MISSING_REQUIRED_FIELD, "Encryption key");
        this.deviceSignature = Require.notNull(deviceSignature, DomainErrorCode.MISSING_REQUIRED_FIELD, "Device signature");
        this.lastActivityAt = Require.notNull(lastActivityAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "Last seen at");
        this.createdAt = Require.notNull(createdAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "Created at");

        this.revokedAt = revokedAt;
    }

    public static Device create(
            DeviceId id, UserId userId,
            DeviceName name, DeviceType type,
            IdentityKey identityKey, EncryptionKey encryptionKey, DeviceSignature deviceSignature
    ) {
        Instant now = Instant.now();
        return new Device(id, userId, name, type, identityKey, encryptionKey, deviceSignature, null, now, now);
    }

    public static Device of(
            DeviceId id, UserId userId,
            DeviceName name, DeviceType type,
            IdentityKey identityKey, EncryptionKey encryptionKey, DeviceSignature deviceSignature,
            Instant revokedAt, Instant lastActivityAt, Instant createdAt
    ) {
        return new Device(id, userId, name, type, identityKey, encryptionKey, deviceSignature, revokedAt, lastActivityAt, createdAt);
    }

    @Override
    public void recordActivity(Instant activityTime) {
        Require.notNull(activityTime, DomainErrorCode.MISSING_REQUIRED_FIELD, "Activity time");

        if (this.lastActivityAt != null && activityTime.isBefore(this.lastActivityAt)) {
            return;
        }

        this.lastActivityAt = Require.after(activityTime, "activityTime", this.createdAt, "createdAt");
    }

    public void revoke() {
        if (this.revokedAt != null) {
            throw new AlreadyRevokedException("Device");
        }
        this.revokedAt = Instant.now();
    }

    public boolean isRevoked() {
        return revokedAt != null;
    }

    public void checkRevoked() {
        if (this.revokedAt != null) {
            throw new RevokedException("Device");
        }
    }

    @Override
    public Instant getLastActivityAt() {
        return lastActivityAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getRevokedAt() {
        return revokedAt;
    }

    public DeviceSignature getDeviceSignature() {
        return deviceSignature;
    }

    public EncryptionKey getEncryptionKey() {
        return encryptionKey;
    }

    public IdentityKey getIdentityKey() {
        return identityKey;
    }

    public DeviceType getType() {
        return type;
    }

    public DeviceName getName() {
        return name;
    }

    public UserId getUserId() {
        return userId;
    }

    public DeviceId getId() {
        return id;
    }
}
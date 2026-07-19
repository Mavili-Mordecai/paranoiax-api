package com.paranoiax.users.domain.models.device;

import com.paranoiax.users.domain.Require;
import com.paranoiax.users.domain.exceptions.DomainErrorCode;
import com.paranoiax.users.domain.models.ActivityTrackable;
import com.paranoiax.users.domain.models.EncryptionKey;
import com.paranoiax.users.domain.models.IdentityKey;
import com.paranoiax.users.domain.models.user.UserId;

import java.time.Instant;

public class Device implements ActivityTrackable {
    private final DeviceId id;
    private final UserId userId;
    private final DeviceName name;
    private final DeviceType type;
    private final IdentityKey identityKey;
    private final EncryptionKey encryptionKey;
    private final DeviceSignature deviceSignature;
    private Instant lastSeenAt;
    private final Instant createdAt;

    private Device(
            DeviceId id, UserId userId,
            DeviceName name, DeviceType type,
            IdentityKey identityKey, EncryptionKey encryptionKey, DeviceSignature deviceSignature,
            Instant lastSeenAt, Instant createdAt
    ) {
        this.id = Require.notNull(id, DomainErrorCode.MISSING_REQUIRED_FIELD, "Id");
        this.userId = Require.notNull(userId, DomainErrorCode.MISSING_REQUIRED_FIELD, "User id");
        this.name = Require.notNull(name, DomainErrorCode.MISSING_REQUIRED_FIELD, "Name");
        this.type = Require.notNull(type, DomainErrorCode.MISSING_REQUIRED_FIELD, "Type");
        this.identityKey = Require.notNull(identityKey, DomainErrorCode.MISSING_REQUIRED_FIELD, "Identity key");
        this.encryptionKey = Require.notNull(encryptionKey, DomainErrorCode.MISSING_REQUIRED_FIELD, "Encryption key");
        this.deviceSignature = Require.notNull(deviceSignature, DomainErrorCode.MISSING_REQUIRED_FIELD, "Device signature");
        this.lastSeenAt = Require.notNull(lastSeenAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "Last seen at");
        this.createdAt = Require.notNull(createdAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "Created at");
    }

    public static Device create(
            DeviceId id, UserId userId,
            DeviceName name, DeviceType type,
            IdentityKey identityKey, EncryptionKey encryptionKey, DeviceSignature deviceSignature
    ) {
        Instant now = Instant.now();
        return new Device(id, userId, name, type, identityKey, encryptionKey, deviceSignature, now, now);
    }

    public static Device of(
            DeviceId id, UserId userId,
            DeviceName name, DeviceType type,
            IdentityKey identityKey, EncryptionKey encryptionKey, DeviceSignature deviceSignature,
            Instant lastSeenAt, Instant createdAt
    ) {
        return new Device(id, userId, name, type, identityKey, encryptionKey, deviceSignature, lastSeenAt, createdAt);
    }

    @Override
    public void recordActivity(Instant activityTime) {
        Require.notNull(activityTime, DomainErrorCode.MISSING_REQUIRED_FIELD, "Activity time");

        if (this.lastSeenAt != null && activityTime.isBefore(this.lastSeenAt)) {
            return;
        }

        this.lastSeenAt = Require.after(activityTime, "activityTime", this.createdAt, "createdAt");
    }

    @Override
    public Instant getLastSeenAt() {
        return lastSeenAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
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
package com.paranoiax.users.domain.models.device;

import com.paranoiax.users.domain.exceptions.DomainValidationException;
import com.paranoiax.users.domain.models.ActivityTrackable;
import com.paranoiax.users.domain.models.EncryptionKey;
import com.paranoiax.users.domain.models.IdentityKey;
import com.paranoiax.users.domain.models.user.UserId;

import java.time.Instant;
import java.util.Objects;

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
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.type = Objects.requireNonNull(type, "type must not be null");
        this.identityKey = Objects.requireNonNull(identityKey, "identityKey must not be null");
        this.encryptionKey = Objects.requireNonNull(encryptionKey, "encryptionKey must not be null");
        this.deviceSignature = Objects.requireNonNull(deviceSignature, "deviceSignature must not be null");
        this.lastSeenAt = Objects.requireNonNull(lastSeenAt, "lastSeenAt must not be null");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
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
        Objects.requireNonNull(activityTime, "Activity time cannot be null");

        if (this.lastSeenAt != null && activityTime.isBefore(this.lastSeenAt)) {
            return;
        }

        if (activityTime.isBefore(this.createdAt)) {
            throw new DomainValidationException("Activity time cannot be before creation time");
        }

        this.lastSeenAt = activityTime;
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
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

    private Device(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.name = builder.name;
        this.type = builder.type;
        this.identityKey = builder.identityKey;
        this.encryptionKey = builder.encryptionKey;
        this.deviceSignature = builder.deviceSignature;
        this.lastSeenAt = builder.lastSeenAt;
        this.createdAt = builder.createdAt;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private DeviceId id;
        private UserId userId;
        private DeviceName name;
        private DeviceType type;
        private IdentityKey identityKey;
        private EncryptionKey encryptionKey;
        private DeviceSignature deviceSignature;
        private Instant lastSeenAt;
        private Instant createdAt;

        public Builder id(DeviceId value) {
            this.id = value;
            return this;
        }

        public Builder userId(UserId value) {
            this.userId = value;
            return this;
        }

        public Builder name(DeviceName value) {
            this.name = value;
            return this;
        }

        public Builder type(DeviceType value) {
            this.type = value;
            return this;
        }

        public Builder identityKey(IdentityKey value) {
            this.identityKey = value;
            return this;
        }

        public Builder encryptionKey(EncryptionKey value) {
            this.encryptionKey = value;
            return this;
        }

        public Builder deviceSignature(DeviceSignature value) {
            this.deviceSignature = value;
            return this;
        }

        public Builder lastSeenAt(Instant value) {
            this.lastSeenAt = value;
            return this;
        }

        public Builder createdAt(Instant value) {
            this.createdAt = value;
            return this;
        }

        public Device build() {
            return new Device(this);
        }
    }
}

package com.paranoiax.users.domain.models.user;

import com.paranoiax.users.domain.exceptions.DomainValidationException;
import com.paranoiax.users.domain.models.ActivityTrackable;
import com.paranoiax.users.domain.models.IdentityKey;
import com.paranoiax.users.domain.models.ImageUrl;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class User implements ActivityTrackable {
    private final UserId id;
    private final IdentityKey identityKey;
    private Username username;
    private Profile profile;
    private final UserId invitedBy;
    private Avatar avatar;
    private Instant lastSeenAt;
    private Instant updatedAt;
    private final Instant createdAt;

    private User(Builder builder) {
        this.id = builder.id;
        this.identityKey = builder.identityKey;
        this.username = builder.username;
        this.profile = builder.profile;
        this.invitedBy = builder.invitedBy;
        this.lastSeenAt = builder.lastSeenAt;
        this.updatedAt = builder.updatedAt;
        this.createdAt = builder.createdAt;
    }

    public static User createNew(IdentityKey identityKey, Username username, UserId invitedBy) {
        Instant now = Instant.now();
        return builder()
                .id(new UserId(UUID.randomUUID()))
                .identityKey(identityKey)
                .username(username)
                .invitedBy(invitedBy)
                .createdAt(now)
                .updatedAt(now)
                .lastSeenAt(now)
                .build();
    }

    public void changeUsername(Username username) {
        Objects.requireNonNull(username, "Username cannot be null");
        this.username = username;
        this.updatedAt = Instant.now();
    }

    public void changeProfile(Profile profile) {
        Objects.requireNonNull(profile, "Profile cannot be null");
        this.profile = profile;
        this.updatedAt = Instant.now();
    }

    public void changeAvatar(ImageUrl small, ImageUrl medium, ImageUrl large) {
        Instant now = Instant.now();

        if (this.avatar == null) {
            this.avatar = new Avatar(this.id, small, medium, large, now);
        } else {
            this.avatar.changeImage(small, medium, large);
        }

        this.updatedAt = now;
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

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public UserId getInvitedBy() {
        return invitedBy;
    }

    public Profile getProfile() {
        return profile;
    }

    public Username getUsername() {
        return username;
    }

    public IdentityKey getIdentityKey() {
        return identityKey;
    }

    public UserId getId() {
        return id;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UserId id;
        private IdentityKey identityKey;
        private Username username;
        private Profile profile;
        private UserId invitedBy;
        private Instant lastSeenAt;
        private Instant updatedAt;
        private Instant createdAt;

        public Builder id(UserId value) {
            this.id = value;
            return this;
        }

        public Builder identityKey(IdentityKey value) {
            this.identityKey = value;
            return this;
        }

        public Builder username(Username value) {
            this.username = value;
            return this;
        }

        public Builder profile(Profile value) {
            this.profile = value;
            return this;
        }

        public Builder invitedBy(UserId value) {
            this.invitedBy = value;
            return this;
        }

        public Builder lastSeenAt(Instant value) {
            this.lastSeenAt = value;
            return this;
        }

        public Builder updatedAt(Instant value) {
            this.updatedAt = value;
            return this;
        }

        public Builder createdAt(Instant value) {
            this.createdAt = value;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
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

    private User(
            UserId id, IdentityKey identityKey, Username username, Profile profile, UserId invitedBy, Avatar avatar,
            Instant lastSeenAt, Instant updatedAt, Instant createdAt
    ) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.identityKey = Objects.requireNonNull(identityKey, "identityKey must not be null");
        this.username = Objects.requireNonNull(username, "username must not be null");
        this.invitedBy = Objects.requireNonNull(invitedBy, "invitedBy must not be null");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
        this.lastSeenAt = Objects.requireNonNull(lastSeenAt, "lastSeenAt must not be null");

        this.profile = profile;
        this.avatar = avatar;
    }

    public static User create(Username username, UserId invitedBy, IdentityKey identityKey) {
        Instant now = Instant.now();
        return new User(
                new UserId(UUID.randomUUID()),
                identityKey,
                username,
                null,
                invitedBy,
                null,
                now,
                now,
                now
        );
    }

    public static User of(
            UserId id, IdentityKey identityKey, Username username, Profile profile, UserId invitedBy, Avatar avatar,
            Instant lastSeenAt, Instant updatedAt, Instant createdAt
    ) {
        return new User(id, identityKey, username, profile, invitedBy, avatar, lastSeenAt, updatedAt, createdAt);
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
}
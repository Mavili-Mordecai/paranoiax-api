package com.paranoiax.users.domain.models.user;

import com.paranoiax.users.domain.Require;
import com.paranoiax.users.domain.exceptions.DomainErrorCode;
import com.paranoiax.users.domain.models.ActivityTrackable;
import com.paranoiax.users.domain.models.IdentityKey;
import com.paranoiax.users.domain.models.ImageUrl;

import java.time.Instant;
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
        this.id = Require.notNull(id, DomainErrorCode.MISSING_REQUIRED_FIELD, "Id");
        this.identityKey = Require.notNull(identityKey, DomainErrorCode.MISSING_REQUIRED_FIELD, "Identity key");
        this.username = Require.notNull(username, DomainErrorCode.MISSING_REQUIRED_FIELD, "Username");
        this.invitedBy = Require.notNull(invitedBy, DomainErrorCode.MISSING_REQUIRED_FIELD, "Invited by");
        this.createdAt = Require.notNull(createdAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "Created at");
        this.updatedAt = Require.notNull(updatedAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "Updated at");
        this.lastSeenAt = Require.notNull(lastSeenAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "Last seen at");

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
        this.username = Require.notNull(username, DomainErrorCode.MISSING_REQUIRED_FIELD, "username");
        this.updatedAt = Instant.now();
    }

    public void changeProfile(Profile profile) {
        this.profile = Require.notNull(profile, DomainErrorCode.MISSING_REQUIRED_FIELD, "profile");
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
        Require.notNull(activityTime, DomainErrorCode.MISSING_REQUIRED_FIELD, "activityTime");

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
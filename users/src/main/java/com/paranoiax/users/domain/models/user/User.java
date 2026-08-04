package com.paranoiax.users.domain.models.user;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;
import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.core.domain.users.UserType;
import com.paranoiax.users.domain.models.ActivityTrackable;
import com.paranoiax.users.domain.models.IdentityKey;

import java.time.Instant;

public class User implements ActivityTrackable {
    private final UserId id;
    private final IdentityKey identityKey;
    private Username username;
    private final UserType type;
    private Profile profile;
    private final UserId invitedBy;
    private Instant lastSeenAt;
    private Instant updatedAt;
    private final Instant createdAt;

    private User(
            UserId id, IdentityKey identityKey,
            Username username, UserType type,
            Profile profile, UserId invitedBy,
            Instant lastSeenAt, Instant updatedAt, Instant createdAt
    ) {
        this.id = Require.notNull(id, DomainErrorCode.MISSING_REQUIRED_FIELD, "Id");
        this.identityKey = Require.notNull(identityKey, DomainErrorCode.MISSING_REQUIRED_FIELD, "Identity key");
        this.username = Require.notNull(username, DomainErrorCode.MISSING_REQUIRED_FIELD, "Username");
        this.type = Require.notNull(type, DomainErrorCode.MISSING_REQUIRED_FIELD, "User type");
        this.lastSeenAt = Require.notNull(lastSeenAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "Last seen at");
        this.updatedAt = Require.notNull(updatedAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "Updated at");
        this.createdAt = Require.notNull(createdAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "Created at");

        this.profile = profile;
        this.invitedBy = invitedBy;
    }

    public static User create(Username username, UserType type, UserId invitedBy, IdentityKey identityKey) {
        Instant now = Instant.now();
        return new User(
                UserId.create(),
                identityKey,
                username,
                type,
                null,
                invitedBy,
                now,
                now,
                now
        );
    }

    public static User of(
            UserId id, IdentityKey identityKey,
            Username username, UserType type,
            Profile profile, UserId invitedBy,
            Instant lastSeenAt, Instant updatedAt, Instant createdAt
    ) {
        return new User(id, identityKey, username, type, profile, invitedBy, lastSeenAt, updatedAt, createdAt);
    }

    public void changeUsername(Username username) {
        this.username = Require.notNull(username, DomainErrorCode.MISSING_REQUIRED_FIELD, "username");
        this.updatedAt = Instant.now();
    }

    public void changeProfile(String data) {
        this.updatedAt = Instant.now();

        if (this.profile != null) {
            this.profile = this.profile.update(data);
            return;
        }

        if (data == null || data.isBlank()) {
            return;
        }

        this.profile = Profile.create(data);
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

    public UserType getType() {
        return type;
    }
}
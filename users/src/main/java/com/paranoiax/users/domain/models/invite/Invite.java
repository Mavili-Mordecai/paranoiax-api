package com.paranoiax.users.domain.models.invite;

import com.paranoiax.users.domain.Require;
import com.paranoiax.users.domain.exceptions.DomainErrorCode;
import com.paranoiax.users.domain.models.user.UserId;

import java.time.Duration;
import java.time.Instant;

public class Invite {
    private final UserId userId;
    private final RegistrationToken registrationToken;
    private final Instant createdAt;
    private final Instant expiresAt;

    public Invite(UserId userId, RegistrationToken registrationToken, Instant createdAt, Instant expiresAt) {
        this.userId = Require.notNull(userId, DomainErrorCode.MISSING_REQUIRED_FIELD, "User ID");
        this.registrationToken = Require.notNull(registrationToken, DomainErrorCode.MISSING_REQUIRED_FIELD, "Registration token");
        this.createdAt = Require.notNull(createdAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "Created at");
        this.expiresAt = Require.notNull(expiresAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "Expires at");
    }

    public static Invite create(UserId createdBy, RegistrationToken registrationToken, Duration ttl) {
        Instant now = Instant.now();
        return new Invite(createdBy, registrationToken, now, now.plus(ttl));
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public RegistrationToken getRegistrationToken() {
        return registrationToken;
    }

    public UserId getUserId() {
        return userId;
    }
}

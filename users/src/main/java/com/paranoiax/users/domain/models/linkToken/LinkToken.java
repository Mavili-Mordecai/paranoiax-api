package com.paranoiax.users.domain.models.linkToken;

import com.paranoiax.users.domain.Require;
import com.paranoiax.users.domain.exceptions.DomainErrorCode;
import com.paranoiax.users.domain.models.ChallengeValue;
import com.paranoiax.users.domain.models.IdentityKey;
import com.paranoiax.users.domain.models.user.UserId;

import java.time.Duration;
import java.time.Instant;

public class LinkToken {
    private final UserId userId;
    private final String token;
    private final ChallengeValue challenge;
    private final IdentityKey identityKey;
    private final Instant createdAt;
    private final Instant expiresAt;

    private LinkToken(UserId userId, String token, ChallengeValue challenge, IdentityKey identityKey, Instant createdAt, Instant expiresAt) {
        this.userId = Require.notNull(userId, DomainErrorCode.MISSING_REQUIRED_FIELD, "userId");
        this.token = Require.notNull(token, DomainErrorCode.MISSING_REQUIRED_FIELD, "token");
        this.challenge = Require.notNull(challenge, DomainErrorCode.MISSING_REQUIRED_FIELD, "challenge");
        this.identityKey = Require.notNull(identityKey, DomainErrorCode.MISSING_REQUIRED_FIELD, "identityKey");
        this.createdAt = Require.notNull(createdAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "createdAt");
        this.expiresAt = Require.notNull(expiresAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "expiresAt");
    }

    public static LinkToken of(UserId userId, String token, ChallengeValue challenge, IdentityKey identityKey, Instant createdAt, Instant expiresAt) {
        return new LinkToken(userId, token, challenge, identityKey, createdAt, expiresAt);
    }

    public static LinkToken create(UserId userId, String token, ChallengeValue challenge, IdentityKey identityKey, Duration ttl) {
        Instant now = Instant.now();
        return new LinkToken(
                userId,
                token,
                challenge,
                identityKey,
                now,
                now.plus(ttl)
        );
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
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

    public ChallengeValue getChallenge() {
        return challenge;
    }

    public String getToken() {
        return token;
    }

    public UserId getUserId() {
        return userId;
    }
}

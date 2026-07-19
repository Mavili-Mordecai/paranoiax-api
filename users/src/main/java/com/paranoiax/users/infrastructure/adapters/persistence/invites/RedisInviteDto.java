package com.paranoiax.users.infrastructure.adapters.persistence.invites;

import com.paranoiax.users.domain.models.invite.RegistrationToken;
import com.paranoiax.users.domain.models.user.UserId;

import java.time.Instant;

public class RedisInviteDto {
    private UserId userId;
    private RegistrationToken registrationToken;
    private Instant createdAt;
    private Instant expiresAt;

    public RedisInviteDto() {

    }

    public RedisInviteDto(UserId userId, RegistrationToken registrationToken, Instant createdAt, Instant expiresAt) {
        this.userId = userId;
        this.registrationToken = registrationToken;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public RegistrationToken getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(RegistrationToken registrationToken) {
        this.registrationToken = registrationToken;
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }
}
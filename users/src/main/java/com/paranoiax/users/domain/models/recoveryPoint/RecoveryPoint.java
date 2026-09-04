package com.paranoiax.users.domain.models.recoveryPoint;

import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.core.domain.IdentityKey;

import java.time.Instant;

public class RecoveryPoint {
    private final RecoveryPointId id;
    private final UserId userId;
    private final IdentityKey identityKey;
    private final RecoveryPointData encryptedData;
    private final Instant createdAt;

    public RecoveryPoint(RecoveryPointId id, UserId userId, IdentityKey identityKey, RecoveryPointData encryptedData, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.identityKey = identityKey;
        this.encryptedData = encryptedData;
        this.createdAt = createdAt;
    }

    public static RecoveryPoint create(UserId userId, IdentityKey identityKey, RecoveryPointData encryptedData) {
        return new RecoveryPoint(RecoveryPointId.create(), userId, identityKey, encryptedData, Instant.now());
    }

    public static RecoveryPoint of(RecoveryPointId id, UserId userId, IdentityKey identityKey, RecoveryPointData encryptedData, Instant createdAt) {
        return new RecoveryPoint(id, userId, identityKey, encryptedData, createdAt);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public RecoveryPointData getEncryptedData() {
        return encryptedData;
    }

    public IdentityKey getIdentityKey() {
        return identityKey;
    }

    public UserId getUserId() {
        return userId;
    }

    public RecoveryPointId getId() {
        return id;
    }
}
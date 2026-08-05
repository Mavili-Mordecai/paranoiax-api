package com.paranoiax.users.domain.models.friendship;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.*;
import com.paranoiax.core.domain.users.UserId;

import java.time.Instant;

public class Friendship {
    private final FriendshipId id;
    private final UserId userId;
    private final UserId friendId;
    private FriendshipStatus status;
    private FriendshipAttributes attributes;
    private Instant updatedAt;
    private final Instant createdAt;

    private Friendship(
            FriendshipId id, UserId userId, UserId friendId,
            FriendshipStatus status, FriendshipAttributes attributes,
            Instant updatedAt, Instant createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
        this.status = status;
        this.attributes = attributes;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public static Friendship income(FriendshipId id, UserId userId, UserId friendId, FriendshipAttributes attributes) {
        return create(id, userId, friendId, FriendshipStatus.INCOME, attributes);
    }

    public static Friendship outcome(FriendshipId id, UserId userId, UserId friendId, FriendshipAttributes attributes) {
        return create(id, userId, friendId, FriendshipStatus.OUTCOME, attributes);
    }

    public static Friendship block(FriendshipId id, UserId userId, UserId friendId, FriendshipAttributes attributes) {
        return create(id, userId, friendId, FriendshipStatus.BLOCKED, attributes);
    }

    public static Friendship of(
            FriendshipId id, UserId userId, UserId friendId,
            FriendshipStatus status, FriendshipAttributes attributes,
            Instant updatedAt, Instant createdAt
    ) {
        return new Friendship(id, userId, friendId, status, attributes, updatedAt, createdAt);
    }

    private static Friendship create(
            FriendshipId id, UserId userId, UserId friendId,
            FriendshipStatus status, FriendshipAttributes attributes
    ) {
        Instant now = Instant.now();
        return new Friendship(id, userId, friendId, status, attributes, now, now);
    }

    public void accept() {
        if (this.status == FriendshipStatus.ACCEPTED) {
            return;
        }

        if (this.status != FriendshipStatus.INCOME && this.status != FriendshipStatus.OUTCOME) {
            throw new InvalidStateTransitionException(this.status.name(), FriendshipStatus.ACCEPTED.name());
        }

        this.status = FriendshipStatus.ACCEPTED;
        this.updatedAt = Instant.now();
    }

    public void block(){
        if (this.status == FriendshipStatus.BLOCKED) {
            return;
        }

        this.status = FriendshipStatus.BLOCKED;
        this.updatedAt = Instant.now();
    }

    public void unblock(FriendshipStatus previousStatus) {
        if (this.status != FriendshipStatus.BLOCKED || previousStatus == FriendshipStatus.BLOCKED) {
            return;
        }

        this.status = previousStatus;
        this.updatedAt = Instant.now();
    }

    public void delete(){
        if (this.status == FriendshipStatus.DELETED) {
            return;
        }

        this.status = FriendshipStatus.DELETED;
        this.updatedAt = Instant.now();
        this.attributes = null;
    }

    public void resurrect(FriendshipStatus status) {
        Require.notNull(status, DomainErrorCode.MISSING_REQUIRED_FIELD, "status");

        if (this.status != FriendshipStatus.DELETED) {
            throw new InvalidStateException("restore friendship", status.name());
        }

        if (status != FriendshipStatus.INCOME && status != FriendshipStatus.OUTCOME) {
            throw new InvalidStateTransitionException(this.status.name(), status.name());
        }

        this.status = status;
        this.updatedAt = Instant.now();
    }

    public void changeAttributes(String data) {
        if (data == null || data.isBlank()) {
            this.attributes = null;
            return;
        }

        if (status.equals(FriendshipStatus.DELETED)) {
            throw new RevokedException("Friendship");
        }

        this.attributes = new FriendshipAttributes(data);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public FriendshipAttributes getAttributes() {
        return attributes;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public UserId getFriendId() {
        return friendId;
    }

    public UserId getUserId() {
        return userId;
    }

    public FriendshipId getId() {
        return id;
    }
}
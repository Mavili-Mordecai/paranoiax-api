package com.paranoiax.users.domain.models.friendship;

import com.paranoiax.core.domain.users.UserId;

import java.time.Instant;

public class Friendship {
    private final FriendshipId id;
    private final UserId userId;
    private final UserId friendId;
    private FriendshipStatus status;
    private FriendshipAttributes attributes;
    private final Instant createdAt;

    private Friendship(
            FriendshipId id, UserId userId, UserId friendId,
            FriendshipStatus status, FriendshipAttributes attributes,
            Instant createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
        this.status = status;
        this.attributes = attributes;
        this.createdAt = createdAt;
    }

    public static Friendship income(FriendshipId id, UserId userId, UserId friendId, FriendshipAttributes attributes) {
        return create(id, userId, friendId, FriendshipStatus.INCOME, attributes);
    }

    public static Friendship outcome(FriendshipId id, UserId userId, UserId friendId, FriendshipAttributes attributes) {
        return create(id, userId, friendId, FriendshipStatus.OUTCOME, attributes);
    }

    public static Friendship of(
            FriendshipId id, UserId userId, UserId friendId,
            FriendshipStatus status, FriendshipAttributes attributes, Instant createdAt
    ) {
        return new Friendship(id, userId, friendId, status, attributes, createdAt);
    }

    private static Friendship create(
            FriendshipId id, UserId userId, UserId friendId,
            FriendshipStatus status, FriendshipAttributes attributes
    ) {
        return new Friendship(id, userId, friendId, status, attributes, Instant.now());
    }

    public void accept() {
        this.status = FriendshipStatus.ACCEPTED;
    }

    public void block(){
        this.status = FriendshipStatus.BLOCKED;
    }

    public void changeAttributes(String data) {
        if (data == null || data.isBlank()) {
            this.attributes = null;
            return;
        }

        this.attributes = new FriendshipAttributes(data);
    }

    public Instant getCreatedAt() {
        return createdAt;
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
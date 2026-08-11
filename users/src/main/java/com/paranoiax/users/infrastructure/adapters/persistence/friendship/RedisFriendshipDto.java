package com.paranoiax.users.infrastructure.adapters.persistence.friendship;

import com.paranoiax.users.domain.models.friendship.FriendshipStatus;

import java.time.Instant;
import java.util.UUID;

public class RedisFriendshipDto {
    private UUID id;
    private UUID userId;
    private UUID friendId;
    private FriendshipStatus status;
    private String attributes;
    private Instant updatedAt;
    private Instant createdAt;

    public RedisFriendshipDto() {

    }

    public RedisFriendshipDto(UUID id, UUID userId, UUID friendId, FriendshipStatus status, String attributes, Instant updatedAt, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
        this.status = status;
        this.attributes = attributes;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }

    public UUID getFriendId() {
        return friendId;
    }

    public void setFriendId(UUID friendId) {
        this.friendId = friendId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
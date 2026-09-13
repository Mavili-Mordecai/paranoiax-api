package com.paranoiax.chats.domain.models.participant;

import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.core.domain.EventsSeq;
import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;
import com.paranoiax.core.domain.users.UserId;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Participant {
    private final ParticipantId id;
    private final ChatId chatId;
    private final UserId userId;
    private ParticipantRole role;
    private boolean isMuted;
    private boolean isPinned;
    private EventsSeq lastReadSeq;
    private Set<ParticipantPermission> permissions;
    private Instant updatedAt;
    private final Instant joinedAt;

    private Participant(
            ParticipantId id, ChatId chatId, UserId userId,
            ParticipantRole role, boolean isMuted, boolean isPinned,
            EventsSeq lastReadSeq,
            Set<ParticipantPermission> permissions,
            Instant updatedAt, Instant joinedAt
    ) {
        this.id = Require.notNull(id, DomainErrorCode.MISSING_REQUIRED_FIELD, "id");
        this.chatId = Require.notNull(chatId, DomainErrorCode.MISSING_REQUIRED_FIELD, "chatId");
        this.userId = Require.notNull(userId, DomainErrorCode.MISSING_REQUIRED_FIELD, "userId");
        this.role = Require.notNull(role, DomainErrorCode.MISSING_REQUIRED_FIELD, "role");
        this.isMuted = Require.notNull(isMuted, DomainErrorCode.MISSING_REQUIRED_FIELD, "isMuted");
        this.isPinned = Require.notNull(isPinned, DomainErrorCode.MISSING_REQUIRED_FIELD, "isPinned");
        this.lastReadSeq = Require.notNull(lastReadSeq, DomainErrorCode.MISSING_REQUIRED_FIELD, "lastReadSeq");
        this.permissions = Require.notNull(permissions, DomainErrorCode.MISSING_REQUIRED_FIELD, "permissions");
        this.updatedAt = Require.notNull(updatedAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "updatedAt");
        this.joinedAt = Require.notNull(joinedAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "joinedAt");
    }

    public static Participant of(
            ParticipantId id, ChatId chatId, UserId userId,
            ParticipantRole role, boolean isMuted, boolean isPinned,
            EventsSeq lastReadSeq,
            Set<ParticipantPermission> permissions,
            Instant updatedAt, Instant joinedAt
    ) {
        return new Participant(
                id, chatId, userId,
                role, isMuted, isPinned,
                lastReadSeq,
                permissions,
                updatedAt, joinedAt
        );
    }

    public static Participant create(
            ChatId chatId, UserId userId,
            ParticipantRole role,
            Set<ParticipantPermission> permissions
    ) {
        Instant now = Instant.now();
        return new Participant(
                ParticipantId.create(), chatId, userId,
                role,
                false, false,
                EventsSeq.create(),
                permissions,
                now, now
        );
    }

    public void assignRole(ParticipantRole role) {
        Require.notNull(role, DomainErrorCode.MISSING_REQUIRED_FIELD, "role");

        if (this.role.equals(role)) {
            return;
        }

        this.role = role;
        this.updatedAt = Instant.now();
    }

    public void commitLastReadSeq(EventsSeq lastReadSeq) {
        if (Objects.equals(this.lastReadSeq.value(), lastReadSeq.value())) {
            return;
        }
        if (this.lastReadSeq.greaterThan(lastReadSeq)) {
            return;
        }
        this.lastReadSeq = Require.notNull(lastReadSeq, DomainErrorCode.MISSING_REQUIRED_FIELD, "lastReadSeq");
        this.updatedAt = Instant.now();
    }

    public void mute() {
        if (this.isMuted) {
            return;
        }

        this.isMuted = true;
        this.updatedAt = Instant.now();
    }

    public void unmute() {
        if (!this.isMuted) {
            return;
        }

        this.isMuted = false;
        this.updatedAt = Instant.now();
    }

    public void pin() {
        if (this.isPinned) {
            return;
        }

        this.isPinned = true;
        this.updatedAt = Instant.now();
    }

    public void unpin() {
        if (!this.isPinned) {
            return;
        }

        this.isPinned = false;
        this.updatedAt = Instant.now();
    }

    public void addPermission(ParticipantPermission permission) {
        Require.notNull(permission, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "permission");

        if (this.permissions == null) {
            this.permissions = new HashSet<>();
        }

        if (this.permissions.add(permission)) {
            this.updatedAt = Instant.now();
        }
    }

    public void removePermission(ParticipantPermission permission) {
        Require.notNull(permission, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "permission");

        if (this.permissions != null && this.permissions.remove(permission)) {
            this.updatedAt = Instant.now();
        }
    }

    public boolean hasPermission(ParticipantPermission permission) {
        Require.notNull(permission, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "permission");

        return this.role == ParticipantRole.OWNER || this.permissions.contains(permission);
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Set<ParticipantPermission> getPermissions() {
        return Set.copyOf(permissions);
    }

    public EventsSeq getLastReadSeq() {
        return lastReadSeq;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public boolean isMuted() {
        return isMuted;
    }

    public ParticipantRole getRole() {
        return role;
    }

    public UserId getUserId() {
        return userId;
    }

    public ChatId getChatId() {
        return chatId;
    }

    public ParticipantId getId() {
        return id;
    }
}

package com.paranoiax.chats.domain.models.chatInvite;

import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;
import com.paranoiax.core.domain.exceptions.UsesCountExceeded;
import com.paranoiax.core.domain.users.UserId;

import java.time.Instant;

public class ChatInvite {
    private final ChatInviteId id;
    private final ChatId chatId;
    private final UserId createdById;
    private ChatInviteUsesCount usesCount;
    private final ChatInviteMaxUses maxUses;
    private final Instant expiresAt;

    public ChatInvite(ChatInviteId id, ChatId chatId, UserId createdById, ChatInviteUsesCount usesCount, ChatInviteMaxUses maxUses, Instant expiresAt) {
        this.id = Require.notNull(id, DomainErrorCode.MISSING_REQUIRED_FIELD, "id");
        this.chatId = Require.notNull(chatId, DomainErrorCode.MISSING_REQUIRED_FIELD, "chatId");
        this.createdById = Require.notNull(createdById, DomainErrorCode.MISSING_REQUIRED_FIELD, "createdById");
        this.usesCount = Require.notNull(usesCount, DomainErrorCode.MISSING_REQUIRED_FIELD, "usesCount");

        this.maxUses = maxUses;
        this.expiresAt = expiresAt;
    }

    public static ChatInvite of(ChatInviteId id, ChatId chatId, UserId createdById, ChatInviteUsesCount usesCount, ChatInviteMaxUses maxUses, Instant expiresAt) {
        return new ChatInvite(id, chatId, createdById, usesCount, maxUses, expiresAt);
    }

    public static ChatInvite create(ChatId chatId, UserId createdById, ChatInviteMaxUses maxUses, Instant expiresAt) {
        return new ChatInvite(ChatInviteId.create(), chatId, createdById, ChatInviteUsesCount.create(), maxUses, expiresAt);
    }

    public void use() {
        if (this.maxUses != null && this.usesCount.greaterThan(this.maxUses)) {
            throw new UsesCountExceeded();
        }

        this.usesCount = this.usesCount.increment();
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public ChatInviteMaxUses getMaxUses() {
        return maxUses;
    }

    public ChatInviteUsesCount getUsesCount() {
        return usesCount;
    }

    public UserId getCreatedById() {
        return createdById;
    }

    public ChatId getChatId() {
        return chatId;
    }

    public ChatInviteId getId() {
        return id;
    }
}

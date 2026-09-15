package com.paranoiax.chats.domain.models.invite;

import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;
import com.paranoiax.core.domain.exceptions.UsesCountExceeded;
import com.paranoiax.core.domain.users.UserId;

import java.time.Instant;

public class Invite {
    private final InviteId id;
    private final ChatId chatId;
    private final UserId createdById;
    private InviteUsesCount usesCount;
    private final InviteMaxUses maxUses;
    private final Instant expiresAt;

    public Invite(InviteId id, ChatId chatId, UserId createdById, InviteUsesCount usesCount, InviteMaxUses maxUses, Instant expiresAt) {
        this.id = Require.notNull(id, DomainErrorCode.MISSING_REQUIRED_FIELD, "id");
        this.chatId = Require.notNull(chatId, DomainErrorCode.MISSING_REQUIRED_FIELD, "chatId");
        this.createdById = Require.notNull(createdById, DomainErrorCode.MISSING_REQUIRED_FIELD, "createdById");
        this.usesCount = Require.notNull(usesCount, DomainErrorCode.MISSING_REQUIRED_FIELD, "usesCount");

        this.maxUses = maxUses;
        this.expiresAt = expiresAt;
    }

    public static Invite of(InviteId id, ChatId chatId, UserId createdById, InviteUsesCount usesCount, InviteMaxUses maxUses, Instant expiresAt) {
        return new Invite(id, chatId, createdById, usesCount, maxUses, expiresAt);
    }

    public static Invite create(ChatId chatId, UserId createdById, InviteMaxUses maxUses, Instant expiresAt) {
        return new Invite(InviteId.create(), chatId, createdById, InviteUsesCount.create(), maxUses, expiresAt);
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

    public InviteMaxUses getMaxUses() {
        return maxUses;
    }

    public InviteUsesCount getUsesCount() {
        return usesCount;
    }

    public UserId getCreatedById() {
        return createdById;
    }

    public ChatId getChatId() {
        return chatId;
    }

    public InviteId getId() {
        return id;
    }
}

package com.paranoiax.chats.domain.models.chatInvite;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;
import com.paranoiax.core.domain.exceptions.InvalidFormatException;

public record ChatInviteUsesCount(int value) {
    public ChatInviteUsesCount {
        Require.notNull(value, DomainErrorCode.MISSING_REQUIRED_FIELD, "usesCount");
        if (value < 0) {
            throw new InvalidFormatException("usesCount");
        }
    }

    public static ChatInviteUsesCount create() {
        return new ChatInviteUsesCount(0);
    }

    public ChatInviteUsesCount increment() {
        return new ChatInviteUsesCount(value + 1);
    }

    public boolean greaterThan(ChatInviteMaxUses maxUses) {
        return value >= maxUses.value();
    }
}

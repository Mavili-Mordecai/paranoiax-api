package com.paranoiax.chats.domain.models.invite;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;
import com.paranoiax.core.domain.exceptions.InvalidFormatException;

public record InviteUsesCount(int value) {
    public InviteUsesCount {
        Require.notNull(value, DomainErrorCode.MISSING_REQUIRED_FIELD, "usesCount");
        if (value < 0) {
            throw new InvalidFormatException("usesCount");
        }
    }

    public static InviteUsesCount create() {
        return new InviteUsesCount(0);
    }

    public InviteUsesCount increment() {
        return new InviteUsesCount(value + 1);
    }

    public boolean greaterThan(InviteMaxUses maxUses) {
        return value >= maxUses.value();
    }
}

package com.paranoiax.chats.domain.models.invite;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;
import com.paranoiax.core.domain.exceptions.InvalidFormatException;

public record InviteMaxUses(int value) {
    public InviteMaxUses {
        Require.notNull(value, DomainErrorCode.MISSING_REQUIRED_FIELD, "maxUses");
        if (value <= 0) {
            throw new InvalidFormatException("maxUses");
        }
    }
}

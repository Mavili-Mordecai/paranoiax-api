package com.paranoiax.core.domain.users;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;

import java.util.UUID;

public record UserId(UUID value) {
    public UserId {
        Require.notNull(value, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "id");
    }

    public static UserId create() {
        return new UserId(UUID.randomUUID());
    }
}

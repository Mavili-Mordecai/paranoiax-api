package com.paranoiax.users.domain.models.user;

import com.paranoiax.users.domain.Require;
import com.paranoiax.users.domain.exceptions.DomainErrorCode;

import java.util.UUID;

public record UserId(UUID value) {
    public UserId {
        Require.notNull(value, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "id");
    }
}

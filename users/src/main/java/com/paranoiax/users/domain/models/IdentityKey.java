package com.paranoiax.users.domain.models;

import com.paranoiax.users.domain.Require;
import com.paranoiax.users.domain.exceptions.DomainErrorCode;

public record IdentityKey(String value) {
    public IdentityKey {
        Require.notNull(value, DomainErrorCode.MISSING_REQUIRED_FIELD, "Identity key");
    }
}
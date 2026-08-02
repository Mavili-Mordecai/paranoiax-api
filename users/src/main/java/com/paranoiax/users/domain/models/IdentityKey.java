package com.paranoiax.users.domain.models;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;

public record IdentityKey(String value) {
    public IdentityKey {
        Require.notNull(value, DomainErrorCode.MISSING_REQUIRED_FIELD, "Identity key");
    }
}
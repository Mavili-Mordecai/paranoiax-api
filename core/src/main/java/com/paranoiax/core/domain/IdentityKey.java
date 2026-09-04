package com.paranoiax.core.domain;

import com.paranoiax.core.domain.exceptions.DomainErrorCode;

public record IdentityKey(String value) {
    public static final int MIN_SIZE = 44;
    public static final int MAX_SIZE = 64;

    public IdentityKey {
        Require.notNull(value, DomainErrorCode.MISSING_REQUIRED_FIELD, "Identity key");
        Require.hasLength(value, "Identity key", MIN_SIZE, MAX_SIZE);
    }
}
package com.paranoiax.users.domain.models;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;

public record EncryptionKey(String value) {
    private static final int MIN_SIZE = 44;
    private static final int MAX_SIZE = 64;

    public EncryptionKey {
        Require.notNull(value, DomainErrorCode.MISSING_REQUIRED_FIELD, "Encryption key");
        Require.hasLength(value, "Encryption key", MIN_SIZE, MAX_SIZE);
    }
}
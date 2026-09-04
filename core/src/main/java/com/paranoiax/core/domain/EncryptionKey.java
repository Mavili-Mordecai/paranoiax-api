package com.paranoiax.core.domain;

import com.paranoiax.core.domain.exceptions.DomainErrorCode;

public record EncryptionKey(String value) {
    public static final int MIN_SIZE = 44;
    public static final int MAX_SIZE = 64;

    public EncryptionKey {
        Require.notNull(value, DomainErrorCode.MISSING_REQUIRED_FIELD, "Encryption key");
        Require.hasLength(value, "Encryption key", MIN_SIZE, MAX_SIZE);
    }
}
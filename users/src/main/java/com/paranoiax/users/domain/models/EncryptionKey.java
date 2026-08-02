package com.paranoiax.users.domain.models;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;

public record EncryptionKey(String value) {
    public EncryptionKey {
        Require.notNull(value, DomainErrorCode.MISSING_REQUIRED_FIELD, "Encryption key");
    }
}
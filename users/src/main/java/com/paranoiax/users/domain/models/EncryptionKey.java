package com.paranoiax.users.domain.models;

import com.paranoiax.users.domain.Require;
import com.paranoiax.users.domain.exceptions.DomainErrorCode;

public record EncryptionKey(String value) {
    public EncryptionKey {
        Require.notNull(value, DomainErrorCode.MISSING_REQUIRED_FIELD, "Encryption key");
    }
}
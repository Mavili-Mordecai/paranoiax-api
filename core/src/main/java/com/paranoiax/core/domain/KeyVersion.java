package com.paranoiax.core.domain;

import com.paranoiax.core.domain.exceptions.DomainErrorCode;
import com.paranoiax.core.domain.exceptions.InvalidFormatException;

public record KeyVersion(int value) {
    public KeyVersion {
        String fieldName = "key_version";
        Require.notNull(value, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, fieldName);
        if (value <= 0) {
            throw new InvalidFormatException(fieldName);
        }
    }

    public static KeyVersion create() {
        return new KeyVersion(1);
    }
}

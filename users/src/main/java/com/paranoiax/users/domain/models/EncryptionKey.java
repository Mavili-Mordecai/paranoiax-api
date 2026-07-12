package com.paranoiax.users.domain.models;

import java.util.Objects;

public record EncryptionKey(String value) {
    public EncryptionKey {
        Objects.requireNonNull(value, "Encryption key cannot be null or blank");
    }
}
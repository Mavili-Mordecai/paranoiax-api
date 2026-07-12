package com.paranoiax.users.domain.models;

import java.util.Objects;

public record IdentityKey(String value) {
    public IdentityKey {
        Objects.requireNonNull(value, "Identity key cannot be null or blank");
    }
}
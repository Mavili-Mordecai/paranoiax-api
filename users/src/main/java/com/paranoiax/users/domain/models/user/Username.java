package com.paranoiax.users.domain.models.user;

import com.paranoiax.users.domain.exceptions.DomainValidationException;

import java.util.Objects;

public record Username(String value) {
    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 32;
    private static final String PATTERN = "^[a-zA-Z0-9_-]{5,32}$";

    public Username {
        Objects.requireNonNull(value, "Username cannot be null or empty");

        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new DomainValidationException("Username must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters long");
        }

        if (!value.matches(PATTERN)) {
            throw new DomainValidationException("Username contains invalid characters");
        }
    }
}

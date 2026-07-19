package com.paranoiax.users.domain.models.user;

import com.paranoiax.users.domain.Require;

public record Username(String value) {
    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 32;
    private static final String PATTERN = "^[a-zA-Z0-9_-]{5,32}$";

    public Username {
        Require.hasLength(value, "username", MIN_LENGTH, MAX_LENGTH);
        Require.matchesIfPresent(value, "username", PATTERN);
    }
}
package com.paranoiax.users.domain.models.user;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;
import com.paranoiax.core.domain.exceptions.InvalidValueException;

public record Profile(String data, Integer version) {
    public static final int MAX_SIZE = 15_000;

    public Profile {
        Require.hasLengthIfPresent(data, "Profile", 1, MAX_SIZE);
        Require.notNull(version, DomainErrorCode.MISSING_REQUIRED_FIELD, "Profile version");

        if (version < 0) {
            throw new InvalidValueException("Profile version");
        }
    }

    public static Profile create(String data) {
        return new Profile(data, 1);
    }

    public Profile update(String data) {
        return new Profile(data, version + 1);
    }
}
package com.paranoiax.users.domain.models.user;

import com.paranoiax.users.domain.Require;

public record Profile(String firstName, String lastName, String bio) {
    private static final int NAME_MAX_LENGTH = 64;
    private static final int BIO_MAX_LENGTH = 128;

    public Profile {
        Require.hasLengthIfPresent(firstName, "First name", 1, NAME_MAX_LENGTH);
        Require.hasLengthIfPresent(lastName, "Last name", 1, NAME_MAX_LENGTH);
        Require.hasLengthIfPresent(bio, "Bio", 1, BIO_MAX_LENGTH);
    }

    public static Profile empty() {
        return new Profile("", "", "");
    }
}

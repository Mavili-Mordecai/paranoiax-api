package com.paranoiax.users.domain.models.user;

import com.paranoiax.users.domain.Require;

public record Profile(String firstName, String lastName, String bio) {
    private static final int NAME_MAX_LENGTH = 64;
    private static final int BIO_MAX_LENGTH = 192;

    public Profile {
        Require.hasLengthIfPresent(firstName, "First name", 1, NAME_MAX_LENGTH);
        Require.hasLengthIfPresent(lastName, "Last name", 1, NAME_MAX_LENGTH);
        Require.hasLengthIfPresent(bio, "Bio", 1, BIO_MAX_LENGTH);
    }

    public static Profile from(ProfileChanges changes) {
        return new Profile(
                changes.firstName() != null && !changes.firstName().isBlank() ? changes.firstName() : null,
                changes.lastName() != null && !changes.lastName().isBlank() ? changes.lastName() : null,
                changes.bio() != null && !changes.bio().isBlank() ? changes.bio() : null
        );
    }

    public Profile mergeWith(ProfileChanges changes) {
        return new Profile(
                changes.firstName() != null ? (changes.firstName().isBlank() ? null : changes.firstName()) : this.firstName,
                changes.lastName() != null ? (changes.lastName().isBlank() ? null : changes.lastName()) : this.lastName,
                changes.bio() != null ? (changes.bio().isBlank() ? null : changes.bio()) : this.bio
        );
    }
}
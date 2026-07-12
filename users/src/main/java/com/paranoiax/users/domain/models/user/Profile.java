package com.paranoiax.users.domain.models.user;

public record Profile(String firstName, String lastName, String bio) {
    private static final int NAME_MAX_LENGTH = 64;
    private static final int BIO_MAX_LENGTH = 128;

    public Profile {
        if (firstName != null && firstName.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("First name must be between 1 and " + NAME_MAX_LENGTH + " characters long");
        }

        if (lastName != null && lastName.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("Last name must be between 1 and " + NAME_MAX_LENGTH + " characters long");
        }

        if (bio != null && bio.length() > BIO_MAX_LENGTH) {
            throw new IllegalArgumentException("Bio must be between 1 and " + BIO_MAX_LENGTH + " characters long");
        }
    }

    public static Profile empty() {
        return new Profile("", "", "");
    }
}

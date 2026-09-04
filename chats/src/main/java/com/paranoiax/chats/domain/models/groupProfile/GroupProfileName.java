package com.paranoiax.chats.domain.models.groupProfile;

import com.paranoiax.core.domain.Require;

public record GroupProfileName(String value) {
    public static final int MIN_SIZE = 1;
    public static final int MAX_SIZE = 128;

    public GroupProfileName {
        Require.hasLength(value, "name", MIN_SIZE, MAX_SIZE);
    }
}

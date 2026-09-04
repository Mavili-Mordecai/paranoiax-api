package com.paranoiax.chats.domain.models.groupProfile;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;
import com.paranoiax.core.domain.exceptions.InvalidFormatException;

import java.net.URI;
import java.net.URISyntaxException;

public record GroupProfileAvatar(String small, String large) {
    public GroupProfileAvatar {
        Require.notNull(small, DomainErrorCode.MISSING_REQUIRED_FIELD, "smallAvatar");
        Require.notNull(large, DomainErrorCode.MISSING_REQUIRED_FIELD, "largeAvatar");

        validateAvatar(small);
        validateAvatar(large);
    }

    private static void validateAvatar(String avatar) {
        try {
            URI uri = new URI(avatar);

            String path = uri.getPath();
            if (path == null) {
                throw new InvalidFormatException("imageUrl");
            }
        } catch (URISyntaxException e) {
            throw new InvalidFormatException("imageUrl");
        }
    }
}

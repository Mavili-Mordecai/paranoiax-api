package com.paranoiax.users.domain.models;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;
import com.paranoiax.core.domain.exceptions.InvalidFormatException;

import java.net.URI;
import java.net.URISyntaxException;

public record ImageUrl(String value) {
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png", ".webp"};

    public ImageUrl {
        Require.notNull(value, DomainErrorCode.MISSING_REQUIRED_FIELD, "Image url");

        try {
            URI uri = new URI(value);

            String path = uri.getPath();
            if (path == null || !hasValidExtension(path)) {
                throw new InvalidFormatException("imageUrl");
            }
        } catch (URISyntaxException e) {
            throw new InvalidFormatException("imageUrl");
        }
    }

    private boolean hasValidExtension(String path) {
        String lowerCasePath = path.toLowerCase();
        for (String ext : ALLOWED_EXTENSIONS) {
            if (lowerCasePath.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }
}

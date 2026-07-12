package com.paranoiax.users.domain.models;

import com.paranoiax.users.domain.exceptions.DomainValidationException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public record ImageUrl(String value) {
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png", ".webp"};

    public ImageUrl {
        Objects.requireNonNull(value, "Avatar URL cannot be null");

        try {
            URI uri = new URI(value);

            String path = uri.getPath();
            if (path == null || !hasValidExtension(path)) {
                throw new DomainValidationException("Avatar URL must point to a valid image format");
            }

        } catch (URISyntaxException e) {
            throw new DomainValidationException("Invalid URL format");
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

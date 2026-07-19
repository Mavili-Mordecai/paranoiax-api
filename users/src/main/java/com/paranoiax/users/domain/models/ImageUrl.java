package com.paranoiax.users.domain.models;

import com.paranoiax.users.domain.Require;
import com.paranoiax.users.domain.exceptions.DomainErrorCode;
import com.paranoiax.users.domain.exceptions.DomainException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public record ImageUrl(String value) {
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png", ".webp"};

    public ImageUrl {
        Require.notNull(value, DomainErrorCode.MISSING_REQUIRED_FIELD, "Image url");

        try {
            URI uri = new URI(value);

            String path = uri.getPath();
            if (path == null || !hasValidExtension(path)) {
                throw new DomainException(
                        DomainErrorCode.INVALID_FORMAT,
                        Map.of("field", "imageUrl"),
                        String.format(DomainErrorCode.INVALID_FORMAT.getDefaultMessage(), value)
                );
            }
        } catch (URISyntaxException e) {
            throw new DomainException(
                    DomainErrorCode.INVALID_FORMAT,
                    Map.of("field", "imageUrl"),
                    String.format(DomainErrorCode.INVALID_FORMAT.getDefaultMessage(), value)
            );
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

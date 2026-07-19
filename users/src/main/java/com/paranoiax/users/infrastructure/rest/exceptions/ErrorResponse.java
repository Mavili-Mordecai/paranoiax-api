package com.paranoiax.users.infrastructure.rest.exceptions;

import java.time.Instant;

public record ErrorResponse<T>(
        String timestamp,
        String traceId,
        String path,
        T content
) {
    public static <T> ErrorResponse<T> of(String traceId, String path, T content) {
        return new ErrorResponse<>(
                Instant.now().toString(),
                traceId,
                path,
                content
        );
    }
}

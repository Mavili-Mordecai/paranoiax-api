package com.paranoiax.core_infra.rest.exceptions;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.Instant;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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

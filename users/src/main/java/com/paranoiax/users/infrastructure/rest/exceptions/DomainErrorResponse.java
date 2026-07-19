package com.paranoiax.users.infrastructure.rest.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

public record DomainErrorResponse(
        String code,
        String message,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        Map<String, Object> args
) {
}

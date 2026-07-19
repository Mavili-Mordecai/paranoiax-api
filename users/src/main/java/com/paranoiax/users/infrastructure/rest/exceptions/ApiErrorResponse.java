package com.paranoiax.users.infrastructure.rest.exceptions;

import java.util.Map;

public record ApiErrorResponse(
        Map<String, String> errors
) {
}

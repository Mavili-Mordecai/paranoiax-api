package com.paranoiax.core_infra.rest.exceptions;

import java.util.Map;

public record ApiErrorResponse(
        Map<String, String> errors
) {
}

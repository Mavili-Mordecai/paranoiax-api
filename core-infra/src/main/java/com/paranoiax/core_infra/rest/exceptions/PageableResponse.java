package com.paranoiax.core_infra.rest.exceptions;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PageableResponse<T>(
        List<T> data,
        boolean hasMore,
        long serverTimeInMillis
) {

}
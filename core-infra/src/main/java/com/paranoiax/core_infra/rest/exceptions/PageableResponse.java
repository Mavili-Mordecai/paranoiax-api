package com.paranoiax.core_infra.rest.exceptions;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PageableResponse<T>(
        List<T> data,
        boolean hasMore,
        long serverTimeInMillis
) {

}
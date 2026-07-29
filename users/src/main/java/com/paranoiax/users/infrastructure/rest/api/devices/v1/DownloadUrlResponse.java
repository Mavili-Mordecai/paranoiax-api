package com.paranoiax.users.infrastructure.rest.api.devices.v1;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record DownloadUrlResponse(
        String downloadUrl,
        String deviceSignature
) {
}

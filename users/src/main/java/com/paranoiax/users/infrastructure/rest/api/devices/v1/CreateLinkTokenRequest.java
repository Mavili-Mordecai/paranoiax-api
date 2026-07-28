package com.paranoiax.users.infrastructure.rest.api.devices.v1;

import jakarta.validation.constraints.NotBlank;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateLinkTokenRequest(
        @NotBlank(message = "FIELD_REQUIRED") String blobId,
        @NotBlank(message = "FIELD_REQUIRED") String identityKey
) {
}
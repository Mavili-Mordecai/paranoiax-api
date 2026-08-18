package com.paranoiax.users.infrastructure.rest.api.recoveryPoint.v1;

import com.paranoiax.users.application.ports.in.recoveryPoint.get.RecoveryPointDetails;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RecoveryPointResponse(
        UUID id,
        String encryptedData
) {
    public static RecoveryPointResponse from(RecoveryPointDetails details) {
        return new RecoveryPointResponse(details.id().value(), details.encryptedData().value());
    }
}
package com.paranoiax.users.infrastructure.rest.api.profile.v1;

import com.paranoiax.users.application.ports.in.profile.getKeys.UserKeysResult;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserKeysResponse(
        UUID userId,
        String identityKey,
        List<UserDeviceInfoResponse> devices
) {
    public static UserKeysResponse from(UserKeysResult userKeysResult) {
        return new UserKeysResponse(
                userKeysResult.userId().value(),
                userKeysResult.identityKey().value(),
                userKeysResult.devices().stream().map(UserDeviceInfoResponse::from).toList()
        );
    }
}
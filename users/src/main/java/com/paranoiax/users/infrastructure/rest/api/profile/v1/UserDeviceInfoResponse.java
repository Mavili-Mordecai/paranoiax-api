package com.paranoiax.users.infrastructure.rest.api.profile.v1;

import com.paranoiax.users.application.ports.in.profile.getKeys.UserDeviceInfo;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.Instant;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserDeviceInfoResponse(
        String deviceId,
        String identityKey,
        String encryptionKey,
        String deviceSignature,
        Instant revokedAt
) {
    public static UserDeviceInfoResponse from(UserDeviceInfo deviceInfo) {
        return new UserDeviceInfoResponse(
                deviceInfo.deviceId().value().toString(),
                deviceInfo.identityKey().value(),
                deviceInfo.encryptionKey().value(),
                deviceInfo.deviceSignature().value(),
                deviceInfo.revokedAt()
        );
    }
}
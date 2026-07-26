package com.paranoiax.users.application.ports.in.auth;

import com.paranoiax.users.domain.models.device.DeviceId;
import com.paranoiax.users.domain.models.device.DeviceType;
import com.paranoiax.users.domain.models.user.UserId;
import com.paranoiax.users.domain.models.user.UserType;

import java.time.Instant;

public class AccessToken extends AuthToken {
    private final UserType type;
    private final DeviceType deviceType;

    public AccessToken(UserId userId, UserType type, DeviceId deviceId, DeviceType deviceType, Instant expiresAt) {
        super(userId, deviceId, expiresAt);
        this.type = type;
        this.deviceType = deviceType;
    }

    public UserType getType() {
        return type;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }
}
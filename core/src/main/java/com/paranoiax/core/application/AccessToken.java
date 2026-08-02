package com.paranoiax.core.application;

import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.core.domain.devices.DeviceType;
import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.core.domain.users.UserType;

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
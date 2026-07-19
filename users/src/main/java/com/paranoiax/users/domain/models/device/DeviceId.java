package com.paranoiax.users.domain.models.device;

import com.paranoiax.users.domain.Require;
import com.paranoiax.users.domain.exceptions.DomainErrorCode;

import java.util.UUID;

public record DeviceId(UUID value) {
    public DeviceId {
        Require.notNull(value, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "deviceId");
    }
}

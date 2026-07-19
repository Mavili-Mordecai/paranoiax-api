package com.paranoiax.users.domain.models.device;

import com.paranoiax.users.domain.Require;
import com.paranoiax.users.domain.exceptions.DomainErrorCode;

public record DeviceName(String value) {
    public DeviceName {
        Require.notNull(value, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "deviceName");
    }
}

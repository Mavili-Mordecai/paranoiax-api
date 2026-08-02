package com.paranoiax.users.domain.models.device;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;

public record DeviceName(String value) {
    public DeviceName {
        Require.notNull(value, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "deviceName");
    }
}

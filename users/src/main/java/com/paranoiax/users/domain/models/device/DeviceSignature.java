package com.paranoiax.users.domain.models.device;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;

public record DeviceSignature(String value) {
    public DeviceSignature {
        Require.notNull(value, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "deviceSignature");
    }
}
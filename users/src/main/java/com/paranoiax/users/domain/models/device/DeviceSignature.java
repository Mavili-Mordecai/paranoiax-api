package com.paranoiax.users.domain.models.device;

import java.util.Objects;

public record DeviceSignature(String value) {
    public DeviceSignature {
        Objects.requireNonNull(value, "Device signature cannot be null or blank");
    }
}
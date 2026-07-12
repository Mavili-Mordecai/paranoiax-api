package com.paranoiax.users.domain.models.device;

import java.util.Objects;

public record DeviceName(String value) {
    public DeviceName {
        Objects.requireNonNull(value, "Device name cannot be null or blank");
    }
}

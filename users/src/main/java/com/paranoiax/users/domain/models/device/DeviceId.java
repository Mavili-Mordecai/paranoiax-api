package com.paranoiax.users.domain.models.device;

import java.util.Objects;
import java.util.UUID;

public record DeviceId(UUID value) {
    public DeviceId {
        Objects.requireNonNull(value, "Id cannot be null");
    }
}

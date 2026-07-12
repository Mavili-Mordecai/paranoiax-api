package com.paranoiax.users.domain.models.user;

import java.util.Objects;
import java.util.UUID;

public record UserId(UUID value) {
    public UserId {
        Objects.requireNonNull(value, "Id cannot be null");
    }
}

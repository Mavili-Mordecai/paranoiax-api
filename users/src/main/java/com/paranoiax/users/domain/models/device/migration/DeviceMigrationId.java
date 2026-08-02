package com.paranoiax.users.domain.models.device.migration;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;

import java.util.UUID;

public record DeviceMigrationId(UUID value) {
    public DeviceMigrationId {
        Require.notNull(value, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "migrationId");
    }

    public static DeviceMigrationId create() {
        return new DeviceMigrationId(UUID.randomUUID());
    }
}
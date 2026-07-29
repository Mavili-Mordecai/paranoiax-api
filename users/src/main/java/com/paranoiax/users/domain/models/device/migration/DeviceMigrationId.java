package com.paranoiax.users.domain.models.device.migration;

import com.paranoiax.users.domain.Require;
import com.paranoiax.users.domain.exceptions.DomainErrorCode;

import java.util.UUID;

public record DeviceMigrationId(UUID value) {
    public DeviceMigrationId {
        Require.notNull(value, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "migrationId");
    }

    public static DeviceMigrationId create() {
        return new DeviceMigrationId(UUID.randomUUID());
    }
}
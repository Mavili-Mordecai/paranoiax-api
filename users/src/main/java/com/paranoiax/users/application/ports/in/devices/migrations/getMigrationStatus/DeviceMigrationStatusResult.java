package com.paranoiax.users.application.ports.in.devices.migrations.getMigrationStatus;

import com.paranoiax.users.domain.models.ChallengeValue;
import com.paranoiax.users.domain.models.device.migration.DeviceMigrationStatus;

public record DeviceMigrationStatusResult(
        DeviceMigrationStatus status,
        ChallengeValue challenge
) {
    public static DeviceMigrationStatusResult of(DeviceMigrationStatus status) {
        return new DeviceMigrationStatusResult(status, null);
    }

    public static DeviceMigrationStatusResult of(DeviceMigrationStatus status, ChallengeValue challenge) {
        return new DeviceMigrationStatusResult(status, challenge);
    }
}

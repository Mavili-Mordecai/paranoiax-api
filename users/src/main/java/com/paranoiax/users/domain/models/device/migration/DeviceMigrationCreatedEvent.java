package com.paranoiax.users.domain.models.device.migration;

import com.paranoiax.users.domain.models.DomainEvent;
import com.paranoiax.users.domain.models.device.DeviceId;
import com.paranoiax.users.domain.models.user.UserId;

import java.time.Instant;

public record DeviceMigrationCreatedEvent(
        DeviceMigrationId deviceMigrationId,
        DeviceId deviceId,
        UserId userId,
        Instant expiresAt,
        Instant occurredOn
) implements DomainEvent {
    public static DeviceMigrationCreatedEvent from(DeviceMigration migration) {
        return new DeviceMigrationCreatedEvent(
                migration.getId(),
                migration.getDeviceId(),
                migration.getUserId(),
                migration.getExpiresAt(),
                migration.getCreatedAt()
        );
    }
}
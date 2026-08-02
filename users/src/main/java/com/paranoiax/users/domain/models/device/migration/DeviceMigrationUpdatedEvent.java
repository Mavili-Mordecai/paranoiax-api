package com.paranoiax.users.domain.models.device.migration;

import com.paranoiax.users.domain.models.ChallengeValue;
import com.paranoiax.users.domain.models.DomainEvent;
import com.paranoiax.users.domain.models.device.DeviceId;
import com.paranoiax.users.domain.models.user.UserId;

import java.time.Instant;

public record DeviceMigrationUpdatedEvent(
        DeviceMigrationId deviceMigrationId,
        DeviceId deviceId,
        UserId userId,
        DeviceMigrationStatus status,
        ChallengeValue challenge,
        Instant expiresAt,
        Instant occurredOn
) implements DomainEvent {
    public static DeviceMigrationUpdatedEvent from(DeviceMigration migration, Instant updatedAt) {
        return new DeviceMigrationUpdatedEvent(
                migration.getId(),
                migration.getDeviceId(),
                migration.getUserId(),
                migration.getStatus(),
                migration.getChallenge(),
                migration.getExpiresAt(),
                updatedAt
        );
    }
}
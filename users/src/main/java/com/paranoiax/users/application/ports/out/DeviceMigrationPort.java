package com.paranoiax.users.application.ports.out;

import com.paranoiax.users.domain.models.device.migration.DeviceMigration;
import com.paranoiax.users.domain.models.device.migration.DeviceMigrationId;

import java.time.Duration;
import java.util.Optional;

public interface DeviceMigrationPort {
    DeviceMigration insert(DeviceMigration migration, Duration ttl);
    DeviceMigration update(DeviceMigration migration, Duration ttl);
    Optional<DeviceMigration> findById(DeviceMigrationId id);
    boolean deleteById(DeviceMigrationId id);
}
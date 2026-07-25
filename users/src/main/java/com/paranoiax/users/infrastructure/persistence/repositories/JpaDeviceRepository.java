package com.paranoiax.users.infrastructure.persistence.repositories;

import com.paranoiax.users.infrastructure.persistence.entities.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaDeviceRepository extends JpaRepository<DeviceEntity, UUID> {
    List<DeviceEntity> findByUserId(UUID userId);
}

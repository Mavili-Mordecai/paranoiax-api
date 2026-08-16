package com.paranoiax.users.infrastructure.adapters.persistence.recoveryPoint;

import com.paranoiax.users.infrastructure.entities.RecoveryPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaRecoveryPointRepository extends JpaRepository<RecoveryPointEntity, UUID> {
    List<RecoveryPointEntity> findAllByUserId(UUID userId);
}
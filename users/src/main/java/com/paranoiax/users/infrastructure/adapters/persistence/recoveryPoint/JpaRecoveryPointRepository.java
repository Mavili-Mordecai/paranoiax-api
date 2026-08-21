package com.paranoiax.users.infrastructure.adapters.persistence.recoveryPoint;

import com.paranoiax.users.infrastructure.entities.RecoveryPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaRecoveryPointRepository extends JpaRepository<RecoveryPointEntity, UUID> {
    Optional<RecoveryPointEntity> findByUserId(UUID userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM RecoveryPointEntity r WHERE r.userId = :userId AND r.id IN :ids")
    void deleteAllByUserIdAndIdIn(UUID userId, List<UUID> ids);
}
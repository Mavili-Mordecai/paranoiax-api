package com.paranoiax.users.infrastructure.adapters.persistence.recoveryPoint;

import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.domain.models.IdentityKey;
import com.paranoiax.users.domain.models.recoveryPoint.RecoveryPoint;
import com.paranoiax.users.domain.models.recoveryPoint.RecoveryPointData;
import com.paranoiax.users.domain.models.recoveryPoint.RecoveryPointId;
import com.paranoiax.users.infrastructure.entities.RecoveryPointEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaRecoveryPointMapper {
    public RecoveryPoint toDomain(RecoveryPointEntity entity) {
        return RecoveryPoint.of(
                new RecoveryPointId(entity.getId()),
                new UserId(entity.getUserId()),
                new IdentityKey(entity.getIdentityKey()),
                new RecoveryPointData(entity.getEncryptedData()),
                entity.getCreatedAt()
        );
    }

    public RecoveryPointEntity toEntity(RecoveryPoint domain) {
        return RecoveryPointEntity.builder()
                .id(domain.getId().value())
                .userId(domain.getUserId().value())
                .identityKey(domain.getIdentityKey().value())
                .encryptedData(domain.getEncryptedData().value())
                .createdAt(domain.getCreatedAt())
                .build();
    }
}
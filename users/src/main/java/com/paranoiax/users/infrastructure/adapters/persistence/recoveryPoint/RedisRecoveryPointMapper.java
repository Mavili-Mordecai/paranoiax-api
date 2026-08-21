package com.paranoiax.users.infrastructure.adapters.persistence.recoveryPoint;


import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.domain.models.IdentityKey;
import com.paranoiax.users.domain.models.recoveryPoint.RecoveryPoint;
import com.paranoiax.users.domain.models.recoveryPoint.RecoveryPointData;
import com.paranoiax.users.domain.models.recoveryPoint.RecoveryPointId;
import com.paranoiax.users.infrastructure.common.operationResultMapper.OperationResultsMapper;
import org.springframework.stereotype.Component;

@Component
public class RedisRecoveryPointMapper implements OperationResultsMapper<RecoveryPoint, RedisRecoveryPointDto> {
    @Override
    public Class<RecoveryPoint> getDomainClass() {
        return RecoveryPoint.class;
    }

    @Override
    public Class<RedisRecoveryPointDto> getEntityClass() {
        return RedisRecoveryPointDto.class;
    }

    @Override
    public RedisRecoveryPointDto toEntity(RecoveryPoint domain) {
        return new RedisRecoveryPointDto(
                domain.getId().value(),
                domain.getUserId().value(),
                domain.getIdentityKey().value(),
                domain.getEncryptedData().value(),
                domain.getCreatedAt()
        );
    }

    @Override
    public RecoveryPoint toDomain(RedisRecoveryPointDto dto) {
        return new RecoveryPoint(
                new RecoveryPointId(dto.getId()),
                new UserId(dto.getUserId()),
                new IdentityKey(dto.getIdentityKey()),
                new RecoveryPointData(dto.getEncryptedData()),
                dto.getCreatedAt()
        );
    }
}

package com.paranoiax.users.infrastructure.adapters.persistence.recoveryPoint;

import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.application.ports.out.RecoveryPointPort;
import com.paranoiax.users.domain.models.recoveryPoint.RecoveryPoint;
import com.paranoiax.users.domain.models.recoveryPoint.RecoveryPointId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaRecoveryPointAdapter implements RecoveryPointPort {
    private final JpaRecoveryPointRepository repository;
    private final JpaRecoveryPointMapper mapper;

    @Override
    public RecoveryPoint save(RecoveryPoint recoveryPoint) {
        return mapper.toDomain(repository.save(mapper.toEntity(recoveryPoint)));
    }

    @Override
    public Optional<RecoveryPoint> find(UserId userId) {
        return repository.findByUserId(userId.value()).map(mapper::toDomain);
    }

    @Override
    public void deleteAll(UserId userId, Collection<RecoveryPointId> ids) {
        repository.deleteAllByUserIdAndIdIn(userId.value(), ids
                .stream()
                .map(RecoveryPointId::value)
                .collect(Collectors.toList())
        );
    }
}
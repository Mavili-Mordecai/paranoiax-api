package com.paranoiax.users.application.ports.out;

import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.domain.models.recoveryPoint.RecoveryPoint;
import com.paranoiax.users.domain.models.recoveryPoint.RecoveryPointId;

import java.util.Collection;
import java.util.Optional;

public interface RecoveryPointPort {
    RecoveryPoint save(RecoveryPoint recoveryPoint);
    Optional<RecoveryPoint> find(UserId userId);
    void deleteAll(UserId userId, Collection<RecoveryPointId> ids);
}
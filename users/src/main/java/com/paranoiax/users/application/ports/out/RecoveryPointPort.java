package com.paranoiax.users.application.ports.out;

import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.domain.models.recoveryPoint.RecoveryPoint;
import com.paranoiax.users.domain.models.recoveryPoint.RecoveryPointId;

import java.util.Collection;
import java.util.List;

public interface RecoveryPointPort {
    RecoveryPoint save(RecoveryPoint recoveryPoint);
    List<RecoveryPoint> findAll(UserId userId);
    void deleteAll(Collection<RecoveryPointId> ids);
}
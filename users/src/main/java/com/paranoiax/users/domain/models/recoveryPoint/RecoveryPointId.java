package com.paranoiax.users.domain.models.recoveryPoint;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;

import java.util.UUID;

public record RecoveryPointId(UUID value) {
    public RecoveryPointId {
        Require.notNull(value, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "id");
    }

    public static RecoveryPointId create() {
        return new RecoveryPointId(UUID.randomUUID());
    }
}
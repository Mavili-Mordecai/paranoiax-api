package com.paranoiax.users.domain.models.recoveryPoint;

import com.paranoiax.core.domain.Require;

public record RecoveryPointData(String value) {
    private final static int MIN_SIZE = 32;
    private final static int MAX_SIZE = 512;

    public RecoveryPointData {
        Require.hasLength(value, "encryptedData", MIN_SIZE, MAX_SIZE);
    }
}
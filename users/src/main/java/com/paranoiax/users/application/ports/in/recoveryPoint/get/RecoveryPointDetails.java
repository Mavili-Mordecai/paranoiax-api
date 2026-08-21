package com.paranoiax.users.application.ports.in.recoveryPoint.get;

import com.paranoiax.users.domain.models.recoveryPoint.RecoveryPoint;
import com.paranoiax.users.domain.models.recoveryPoint.RecoveryPointData;
import com.paranoiax.users.domain.models.recoveryPoint.RecoveryPointId;

public record RecoveryPointDetails(
        RecoveryPointId id,
        RecoveryPointData encryptedData
) {
    public static RecoveryPointDetails from(RecoveryPoint recoveryPoint) {
        return new RecoveryPointDetails(recoveryPoint.getId(), recoveryPoint.getEncryptedData());
    }
}

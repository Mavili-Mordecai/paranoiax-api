package com.paranoiax.users.application.ports.in.recoveryPoint.get;

import java.util.UUID;

public record AccessRecoveryPointCommand(
        String challenge,
        String signature,
        UUID deviceId
) {
}

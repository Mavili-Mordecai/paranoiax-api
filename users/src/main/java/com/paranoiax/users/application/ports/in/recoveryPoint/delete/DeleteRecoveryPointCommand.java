package com.paranoiax.users.application.ports.in.recoveryPoint.delete;

import java.util.UUID;

public record DeleteRecoveryPointCommand(
        UUID id,
        UUID userId
) {
}

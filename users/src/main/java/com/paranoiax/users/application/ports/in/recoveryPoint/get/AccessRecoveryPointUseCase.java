package com.paranoiax.users.application.ports.in.recoveryPoint.get;

public interface AccessRecoveryPointUseCase {
    RecoveryPointDetails execute(AccessRecoveryPointCommand command);
}

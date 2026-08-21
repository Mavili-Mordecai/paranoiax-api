package com.paranoiax.users.application.services.recoveryPoint;

import com.paranoiax.core.domain.exceptions.AlreadyExistsException;
import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.application.ports.in.recoveryPoint.create.CreateRecoveryPointCommand;
import com.paranoiax.users.application.ports.in.recoveryPoint.create.CreateRecoveryPointUseCase;
import com.paranoiax.users.application.ports.out.RecoveryPointPort;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.domain.models.IdentityKey;
import com.paranoiax.users.domain.models.recoveryPoint.RecoveryPoint;
import com.paranoiax.users.domain.models.recoveryPoint.RecoveryPointData;

import java.time.Duration;

public class CreateRecoveryPointService implements CreateRecoveryPointUseCase {
    private final RecoveryPointPort port;
    private final OperationExecutor executor;
    private final Duration lockTtl;
    private final Duration resultTtl;

    public CreateRecoveryPointService(
            RecoveryPointPort port,
            OperationExecutor executor,
            Duration lockTtl,
            Duration resultTtl
    ) {
        this.port = port;
        this.executor = executor;
        this.lockTtl = lockTtl;
        this.resultTtl = resultTtl;
    }

    @Override
    public void execute(CreateRecoveryPointCommand command) {
        executor.execute(command, RecoveryPoint.class, lockTtl, resultTtl, () -> {
            UserId userId = new UserId(command.userId());
            if (port.find(userId).isPresent()) {
                throw new AlreadyExistsException("Recovery point");
            }

            return port.save(RecoveryPoint.create(
                    userId,
                    new IdentityKey(command.identityKey()),
                    new RecoveryPointData(command.encryptedData())
            ));
        });
    }
}

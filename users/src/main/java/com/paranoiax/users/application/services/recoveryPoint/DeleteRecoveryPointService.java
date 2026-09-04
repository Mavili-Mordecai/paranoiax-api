package com.paranoiax.users.application.services.recoveryPoint;

import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.application.ports.in.recoveryPoint.delete.DeleteRecoveryPointCommand;
import com.paranoiax.users.application.ports.in.recoveryPoint.delete.DeleteRecoveryPointUseCase;
import com.paranoiax.users.application.ports.out.RecoveryPointPort;
import com.paranoiax.core.application.ports.out.TransactionPort;
import com.paranoiax.users.domain.models.recoveryPoint.RecoveryPointId;

import java.util.List;

public class DeleteRecoveryPointService implements DeleteRecoveryPointUseCase {
    private final RecoveryPointPort port;
    private final TransactionPort transactionPort;

    public DeleteRecoveryPointService(
            RecoveryPointPort port,
            TransactionPort transactionPort
    ) {
        this.port = port;
        this.transactionPort = transactionPort;
    }

    @Override
    public void execute(DeleteRecoveryPointCommand command) {
        transactionPort.execute(() -> {
            port.deleteAll(
                    new UserId(command.userId()),
                    List.of(new RecoveryPointId(command.id()))
            );
        });
    }
}

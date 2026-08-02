package com.paranoiax.users.application.services;

import com.paranoiax.users.application.exceptions.LockAcquisitionFailedException;
import com.paranoiax.users.application.ports.out.operationResult.OperationCommand;
import com.paranoiax.users.application.ports.out.operationResult.OperationRecord;
import com.paranoiax.users.application.ports.out.operationResult.OperationResultPort;
import com.paranoiax.users.application.ports.out.TransactionPort;
import com.paranoiax.users.application.ports.out.crypto.HashPort;
import com.paranoiax.core.domain.exceptions.OperationParametersChangedException;

import java.time.Duration;
import java.util.Optional;
import java.util.function.Supplier;

public class OperationExecutor {
    private final OperationResultPort operationResultPort;
    private final TransactionPort transactionPort;
    private final HashPort hashPort;

    public OperationExecutor(OperationResultPort operationResultPort, TransactionPort transactionPort, HashPort hashPort) {
        this.operationResultPort = operationResultPort;
        this.transactionPort = transactionPort;
        this.hashPort = hashPort;
    }

    public <T> T execute(
            OperationCommand command,
            Class<T> resultType,
            Duration lockTtl,
            Duration resultTtl,
            Supplier<T> operation
    ) {
        String payloadSignature = hashPort.sha256Hex(command.getPayloadSignature());

        Optional<OperationRecord<T>> savedResult = operationResultPort.findResult(command.operationId(), resultType);

        if (savedResult.isPresent()) {
             OperationRecord<T> record = savedResult.get();

             if (!record.getPayloadSignature().equals(payloadSignature)) {
                 throw new OperationParametersChangedException();
             }

             return record.getResult();
        }

        boolean isLock = operationResultPort.tryLock(command.operationId(), lockTtl);
        if (!isLock) {
            throw new LockAcquisitionFailedException(command.operationId());
        }

        try {
            T result = transactionPort.execute(operation);
            operationResultPort.saveResult(command.operationId(), resultType, new OperationRecord<>(payloadSignature, result), resultTtl);
            return result;
        } finally {
            operationResultPort.unlock(command.operationId());
        }
    }
}
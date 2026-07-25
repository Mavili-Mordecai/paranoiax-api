package com.paranoiax.users.application.services;

import com.paranoiax.users.application.exceptions.LockAcquisitionFailedException;
import com.paranoiax.users.application.ports.out.OperationResultPort;
import com.paranoiax.users.application.ports.out.TransactionPort;

import java.time.Duration;
import java.util.Optional;
import java.util.function.Supplier;

public class OperationExecutor {
    private final OperationResultPort operationResultPort;
    private final TransactionPort transactionPort;

    public OperationExecutor(OperationResultPort operationResultPort, TransactionPort transactionPort) {
        this.operationResultPort = operationResultPort;
        this.transactionPort = transactionPort;
    }

    public <T> T execute(
            String operationId,
            Class<T> resultType,
            Duration lockTtl,
            Duration resultTtl,
            Supplier<T> operation
    ) {
        Optional<T> savedResult = operationResultPort.findResult(operationId, resultType);
        if (savedResult.isPresent()) {
            return savedResult.get();
        }

        boolean isLock = operationResultPort.tryLock(operationId, lockTtl);
        if (!isLock) {
            throw new LockAcquisitionFailedException(operationId);
        }

        try {
            T result = transactionPort.execute(operation);
            operationResultPort.saveResult(operationId, result, resultTtl);
            return result;
        } finally {
            operationResultPort.unlock(operationId);
        }
    }
}
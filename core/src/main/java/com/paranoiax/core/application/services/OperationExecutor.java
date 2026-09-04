package com.paranoiax.core.application.services;

import com.paranoiax.core.application.exceptions.LockAcquisitionFailedException;
import com.paranoiax.core.application.ports.out.CanonicalizerPort;
import com.paranoiax.core.application.ports.out.TransactionPort;
import com.paranoiax.core.application.ports.out.crypto.HashPort;
import com.paranoiax.core.application.OperationCommand;
import com.paranoiax.core.application.ports.out.operationResult.OperationRecord;
import com.paranoiax.core.application.ports.out.operationResult.OperationResultPort;
import com.paranoiax.core.domain.exceptions.OperationParametersChangedException;

import java.time.Duration;
import java.util.Optional;
import java.util.function.Supplier;

public class OperationExecutor {
    private final OperationResultPort operationResultPort;
    private final CanonicalizerPort canonicalizerPort;
    private final TransactionPort transactionPort;
    private final HashPort hashPort;

    public OperationExecutor(
            OperationResultPort operationResultPort,
            CanonicalizerPort canonicalizerPort,
            TransactionPort transactionPort,
            HashPort hashPort
    ) {
        this.operationResultPort = operationResultPort;
        this.canonicalizerPort = canonicalizerPort;
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
        String payloadSignature = getPayload(command);

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

    private String getPayload(OperationCommand command) {
        return hashPort.sha256Hex(canonicalizerPort.canonicalize(command));
    }
}
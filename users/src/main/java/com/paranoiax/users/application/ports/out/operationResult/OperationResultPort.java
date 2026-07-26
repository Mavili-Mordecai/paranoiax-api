package com.paranoiax.users.application.ports.out.operationResult;

import java.time.Duration;
import java.util.Optional;

public interface OperationResultPort {
    boolean tryLock(String operationId, Duration ttl);
    boolean unlock(String operationId);
    <T> Optional<OperationRecord<T>> findResult(String operationId, Class<T> clazz);
    <T> void saveResult(String operationId, Class<T> clazz, OperationRecord<T> operation, Duration ttl);
}

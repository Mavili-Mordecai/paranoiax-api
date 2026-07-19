package com.paranoiax.users.application.ports.out;

import java.time.Duration;
import java.util.Optional;

public interface OperationResultPort {
    boolean tryLock(String operationId, Duration ttl);
    <T> Optional<T> findResult(String operationId, Class<T> clazz);
    <T> void saveResult(String operationId, T result, Duration ttl);
}

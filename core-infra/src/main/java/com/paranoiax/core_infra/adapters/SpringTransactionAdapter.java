package com.paranoiax.core_infra.adapters;

import com.paranoiax.core.application.ports.out.TransactionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.support.TransactionOperations;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class SpringTransactionAdapter implements TransactionPort {
    private final TransactionOperations transactionOperations;

    @Override
    public <T> T execute(Supplier<T> operation) {
        return transactionOperations.execute(status -> operation.get());
    }

    @Override
    public void execute(Runnable operation) {
        transactionOperations.executeWithoutResult(status -> operation.run());
    }
}

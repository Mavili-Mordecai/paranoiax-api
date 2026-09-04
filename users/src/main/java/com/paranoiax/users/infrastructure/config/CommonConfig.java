package com.paranoiax.users.infrastructure.config;

import com.paranoiax.core.application.ports.out.CanonicalizerPort;
import com.paranoiax.core.application.ports.out.crypto.HashPort;
import com.paranoiax.core.application.ports.out.operationResult.OperationResultPort;
import com.paranoiax.core.application.ports.out.TransactionPort;
import com.paranoiax.core.application.services.OperationExecutor;
import com.paranoiax.core_infra.adapters.JsonCanonicalizerAdapter;
import com.paranoiax.core_infra.adapters.SpringTransactionAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionOperations;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class CommonConfig {

    @Bean
    public CanonicalizerPort canonicalizerPort(ObjectMapper objectMapper) {
        return new JsonCanonicalizerAdapter(objectMapper);
    }

    @Bean
    public OperationExecutor operationExecutor(
            OperationResultPort operationResultPort,
            CanonicalizerPort canonicalizerPort,
            TransactionPort transactionPort,
            HashPort hashPort
    ) {
        return new OperationExecutor(operationResultPort, canonicalizerPort, transactionPort, hashPort);
    }

    @Bean
    public TransactionPort transactionPort(
            TransactionOperations transactionOperations
    ) {
        return new SpringTransactionAdapter(transactionOperations);
    }
}
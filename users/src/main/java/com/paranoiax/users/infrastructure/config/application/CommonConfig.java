package com.paranoiax.users.infrastructure.config.application;

import com.paranoiax.users.application.ports.out.crypto.HashPort;
import com.paranoiax.users.application.ports.out.operationResult.OperationResultPort;
import com.paranoiax.users.application.ports.out.TransactionPort;
import com.paranoiax.users.application.services.OperationExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

    @Bean
    public OperationExecutor operationExecutor(OperationResultPort operationResultPort, TransactionPort transactionPort, HashPort hashPort) {
        return new OperationExecutor(operationResultPort, transactionPort, hashPort);
    }
}
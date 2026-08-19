package com.paranoiax.users.infrastructure.config.application;

import com.paranoiax.users.application.ports.in.recoveryPoint.challenge.CreateRecoveryPointChallengeUseCase;
import com.paranoiax.users.application.ports.in.recoveryPoint.create.CreateRecoveryPointUseCase;
import com.paranoiax.users.application.ports.in.recoveryPoint.delete.DeleteRecoveryPointUseCase;
import com.paranoiax.users.application.ports.in.recoveryPoint.get.AccessRecoveryPointUseCase;
import com.paranoiax.users.application.ports.out.*;
import com.paranoiax.users.application.ports.out.crypto.SignatureVerifierPort;
import com.paranoiax.users.application.ports.out.crypto.TokenGenerator;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.application.services.RecoveryChallengeValidator;
import com.paranoiax.users.application.services.recoveryPoint.CreateRecoveryPointChallengeService;
import com.paranoiax.users.application.services.recoveryPoint.CreateRecoveryPointService;
import com.paranoiax.users.application.services.recoveryPoint.DeleteRecoveryPointService;
import com.paranoiax.users.application.services.recoveryPoint.AccessRecoveryPointService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RecoveryPointConfig {

    @Bean
    public RecoveryChallengeValidator recoveryChallengeValidator(
            CanonicalizerPort canonicalizerPort,
            SignatureVerifierPort verifierPort
    ) {
        return new RecoveryChallengeValidator(canonicalizerPort, verifierPort);
    }

    @Bean
    public CreateRecoveryPointUseCase createRecoveryPointUseCase(
            RecoveryPointPort port,
            OperationExecutor executor,
            @Value("${application.recovery-point.challenge.lock-ttl}") Duration lockTtl,
            @Value("${application.recovery-point.challenge.result-ttl}") Duration resultTtl
    ) {
        return new CreateRecoveryPointService(
                port,
                executor,
                lockTtl,
                resultTtl
        );
    }

    @Bean
    public CreateRecoveryPointChallengeUseCase createRecoveryPointChallengeUseCase(
            ChallengePort challengePort,
            UserPort userPort,
            TokenGenerator tokenGenerator,
            OperationExecutor executor,
            @Value("${application.recovery-point.challenge.lock-ttl}") Duration lockTtl,
            @Value("${application.recovery-point.challenge.result-ttl}") Duration resultTll,
            @Value("${application.recovery-point.challenge.token-size}") int tokenSize
    ) {
        return new CreateRecoveryPointChallengeService(
                challengePort,
                userPort,
                tokenGenerator,
                executor,
                lockTtl,
                resultTll,
                tokenSize
        );
    }

    @Bean
    public AccessRecoveryPointUseCase accessRecoveryPointUseCase(
            RecoveryPointPort recoveryPointPort,
            ChallengePort challengePort,
            RecoveryChallengeValidator validator
    ) {
        return new AccessRecoveryPointService(recoveryPointPort, challengePort, validator);
    }

    @Bean
    public DeleteRecoveryPointUseCase deleteRecoveryPointUseCase(
            RecoveryPointPort port,
            TransactionPort transactionPort
    ) {
        return new DeleteRecoveryPointService(port, transactionPort);
    }
}

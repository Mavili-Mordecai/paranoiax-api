package com.paranoiax.users.infrastructure.config.application;

import com.paranoiax.users.application.ports.in.auth.challengeAuth.ChallengeAuthUseCase;
import com.paranoiax.users.application.ports.in.auth.createChallenge.CreateChallengeUseCase;
import com.paranoiax.users.application.ports.in.auth.invite.InviteUserUseCase;
import com.paranoiax.users.application.ports.in.auth.refreshTokens.RefreshTokensUseCase;
import com.paranoiax.users.application.ports.in.auth.register.RegisterUserUseCase;
import com.paranoiax.users.application.ports.out.*;
import com.paranoiax.users.application.ports.out.crypto.SignatureVerifierPort;
import com.paranoiax.users.application.ports.out.crypto.TokenGenerator;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.application.services.auth.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class AuthConfig {

    @Bean
    public InviteUserUseCase inviteUserUseCase(
            InvitePort invitePort,
            UserPort userPort,
            TokenGenerator tokenGenerator,
            OperationExecutor executor,
            @Value("${application.auth.invite.lock-ttl}") Duration lockTtl,
            @Value("${application.auth.invite.result-ttl}") Duration resultTtl,
            @Value("${application.auth.invite.token-size}") int tokenSize
    ) {
        return new InviteUserService(invitePort, userPort, tokenGenerator, executor, lockTtl, resultTtl, tokenSize);
    }

    @Bean
    public RegisterUserUseCase registerUserUseCase(
            UserPort userPort,
            DevicePort devicePort,
            InvitePort invitePort,
            OperationExecutor executor,
            @Value("${application.auth.register.invite-only}") boolean isInviteOnly,
            @Value("${application.auth.register.lock-ttl}") Duration lockTtl,
            @Value("${application.auth.register.result-ttl}") Duration resultTtl
    ) {
        return new RegisterUserService(userPort, devicePort, invitePort, executor, isInviteOnly, lockTtl, resultTtl);
    }

    @Bean
    public CreateChallengeUseCase createChallengeUseCase(
            ChallengePort challengePort,
            DevicePort devicePort,
            TokenGenerator tokenGenerator,
            OperationExecutor executor,
            @Value("${application.auth.challenge.lock-ttl}") Duration lockTtl,
            @Value("${application.auth.challenge.result-ttl}") Duration resultTll,
            @Value("${application.auth.challenge.token-size}") int tokenSize
    ) {
        return new CreateChallengeService(challengePort, devicePort, tokenGenerator, executor, lockTtl, resultTll, tokenSize);
    }

    @Bean
    public ChallengeAuthUseCase challengeAuthUseCase(
            DevicePort devicePort,
            ChallengePort challengePort,
            SignatureVerifierPort verifierPort,
            AuthTokenPort authTokenPort,
            OperationExecutor executor,
            @Value("${application.auth.challenge-auth.lock-ttl}") Duration lockTtl,
            @Value("${application.auth.challenge-auth.result-ttl}") Duration resultTtl
    ) {
        return new ChallengeAuthService(devicePort, challengePort, verifierPort, authTokenPort, executor, lockTtl, resultTtl);
    }

    @Bean
    public RefreshTokensUseCase refreshTokensUseCase(
            AuthTokenPort authTokenPort,
            AuthTokenBlacklistPort blacklistPort,
            DevicePort devicePort,
            OperationExecutor executor,
            @Value("${application.auth.refresh.lock-ttl}") Duration lockTtl,
            @Value("${application.auth.refresh.result-ttl}") Duration resultTtl
    ) {
        return new RefreshTokensService(authTokenPort, blacklistPort, devicePort, executor, lockTtl, resultTtl);
    }
}
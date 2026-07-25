package com.paranoiax.users.infrastructure.config.application;

import com.paranoiax.users.application.ports.in.auth.invite.InviteUserUseCase;
import com.paranoiax.users.application.ports.in.auth.register.RegisterUserUseCase;
import com.paranoiax.users.application.ports.out.*;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.application.services.auth.InviteUserService;
import com.paranoiax.users.application.services.auth.RegisterUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class AuthConfig {

    @Bean
    public InviteUserUseCase inviteUserUseCase(
            InvitePort invitePort,
            TokenGenerator tokenGenerator,
            OperationExecutor executor,
            @Value("${application.invite.lock-ttl}") Duration lockTtl,
            @Value("${application.invite.result-ttl}") Duration resultTtl
    ) {
        return new InviteUserService(invitePort, tokenGenerator, executor, lockTtl, resultTtl);
    }

    @Bean
    public RegisterUserUseCase registerUserUseCase(
            UserPort userPort,
            DevicePort devicePort,
            InvitePort invitePort,
            OperationExecutor executor,
            @Value("${application.register.lock-ttl}") Duration lockTtl,
            @Value("${application.register.result-ttl}") Duration resultTtl
    ) {
        return new RegisterUserService(userPort, devicePort, invitePort, executor, lockTtl, resultTtl);
    }
}

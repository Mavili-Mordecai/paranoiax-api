package com.paranoiax.users.infrastructure.config.application;

import com.paranoiax.users.application.ports.in.auth.invite.InviteUserUseCase;
import com.paranoiax.users.application.ports.out.InvitePort;
import com.paranoiax.users.application.ports.out.OperationResultPort;
import com.paranoiax.users.application.ports.out.TokenGenerator;
import com.paranoiax.users.application.services.auth.InviteUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {

    @Bean
    public InviteUserUseCase inviteUserUseCase(
            InvitePort invitePort,
            OperationResultPort operationResultPort,
            TokenGenerator tokenGenerator,
            @Value("${application.invite.lock-ttl}") Long lockTtl,
            @Value("${application.invite.token-ttl}") Long tokenTtl
    ) {
        return new InviteUserService(invitePort, operationResultPort, tokenGenerator, lockTtl, tokenTtl);
    }
}

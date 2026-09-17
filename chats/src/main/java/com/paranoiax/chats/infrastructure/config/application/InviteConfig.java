package com.paranoiax.chats.infrastructure.config.application;

import com.paranoiax.chats.application.ports.in.invite.create.CreateInviteUseCase;
import com.paranoiax.chats.application.ports.in.invite.use.UseInviteUseCase;
import com.paranoiax.chats.application.ports.out.ChatPort;
import com.paranoiax.chats.application.ports.out.InvitePort;
import com.paranoiax.chats.application.ports.out.ParticipantKeyPort;
import com.paranoiax.chats.application.ports.out.ParticipantPort;
import com.paranoiax.chats.application.services.invite.CreateInviteService;
import com.paranoiax.chats.application.services.invite.UseInviteService;
import com.paranoiax.core.application.services.OperationExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class InviteConfig {

    @Bean
    public CreateInviteUseCase createInviteUseCase(
            InvitePort invitePort,
            ParticipantPort participantPort,
            OperationExecutor executor,
            @Value("${application.chat.lock-ttl}") Duration lockTtl,
            @Value("${application.chat.result-ttl}") Duration resultTtl
    ) {
        return new CreateInviteService(invitePort, participantPort, executor, lockTtl, resultTtl);
    }

    @Bean
    public UseInviteUseCase useInviteUseCase(
            InvitePort invitePort,
            ChatPort chatPort,
            ParticipantPort participantPort,
            ParticipantKeyPort participantKeyPort,
            OperationExecutor executor,
            @Value("${application.chat.lock-ttl}") Duration lockTtl,
            @Value("${application.chat.result-ttl}") Duration resultTtl
    ) {
        return new UseInviteService(
                invitePort,
                chatPort,
                participantPort,
                participantKeyPort,
                executor,
                lockTtl,
                resultTtl
        );
    }
}

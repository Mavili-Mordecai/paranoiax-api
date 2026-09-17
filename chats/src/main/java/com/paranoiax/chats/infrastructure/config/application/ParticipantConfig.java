package com.paranoiax.chats.infrastructure.config.application;

import com.paranoiax.chats.application.ports.in.participant.addParticipant.AddParticipantsToChatUseCase;
import com.paranoiax.chats.application.ports.out.ChatPort;
import com.paranoiax.chats.application.ports.out.ParticipantKeyPort;
import com.paranoiax.chats.application.ports.out.ParticipantPort;
import com.paranoiax.chats.application.services.chat.AddParticipantsToChatService;
import com.paranoiax.core.application.services.OperationExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ParticipantConfig {

    @Bean
    public AddParticipantsToChatUseCase addParticipantsToChatUseCase(
            ChatPort chatPort,
            ParticipantPort participantPort,
            ParticipantKeyPort participantKeyPort,
            OperationExecutor executor,
            @Value("${application.chat.lock-ttl}") Duration lockTtl,
            @Value("${application.chat.result-ttl}") Duration resultTtl
    ) {
        return new AddParticipantsToChatService(chatPort, participantPort, participantKeyPort, executor, lockTtl, resultTtl);
    }
}

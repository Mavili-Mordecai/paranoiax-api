package com.paranoiax.chats.infrastructure.config.application;

import com.paranoiax.chats.application.ports.in.key.findAll.FindAllPendingKeysUseCase;
import com.paranoiax.chats.application.ports.out.ParticipantKeyPort;
import com.paranoiax.chats.application.ports.out.ParticipantPort;
import com.paranoiax.chats.application.services.key.FindAllPendingKeysService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyConfig {

    @Bean
    public FindAllPendingKeysUseCase findAllPendingKeysUseCase(
            ParticipantPort participantPort,
            ParticipantKeyPort participantKeyPort
    ) {
        return new FindAllPendingKeysService(participantKeyPort, participantPort);
    }
}

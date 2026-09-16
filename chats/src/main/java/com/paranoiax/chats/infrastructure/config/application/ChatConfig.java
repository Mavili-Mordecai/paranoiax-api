package com.paranoiax.chats.infrastructure.config.application;

import com.paranoiax.chats.application.ports.in.chat.addParticipant.AddParticipantsToChatUseCase;
import com.paranoiax.chats.application.ports.in.chat.create.CreateChatUseCase;
import com.paranoiax.chats.application.ports.in.chat.findAll.FindChatsUseCase;
import com.paranoiax.chats.application.ports.in.invite.create.CreateInviteUseCase;
import com.paranoiax.chats.application.ports.in.invite.use.UseInviteUseCase;
import com.paranoiax.chats.application.ports.out.*;
import com.paranoiax.chats.application.services.chat.AddParticipantsToChatService;
import com.paranoiax.chats.application.services.chat.CreateChatService;
import com.paranoiax.chats.application.services.chat.FindChatsService;
import com.paranoiax.chats.application.services.invite.CreateInviteService;
import com.paranoiax.chats.application.services.invite.UseInviteService;
import com.paranoiax.core.application.services.OperationExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ChatConfig {

    @Bean
    public CreateChatUseCase createChatUseCase(
            OperationExecutor executor,
            ChatPort chatPort,
            GroupProfilePort groupProfilePort,
            ParticipantPort participantPort,
            ParticipantKeyPort participantKeyPort,
            @Value("${application.chat.lock-ttl}") Duration lockTtl,
            @Value("${application.chat.result-ttl}") Duration resultTtl
    ) {
        return new CreateChatService(
                executor,
                chatPort,
                groupProfilePort,
                participantPort,
                participantKeyPort,
                lockTtl,
                resultTtl
        );
    }

    @Bean
    public FindChatsUseCase findChatsUseCase(
            ChatPort chatPort,
            GroupProfilePort groupProfilePort
    ) {
        return new FindChatsService(chatPort, groupProfilePort);
    }

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

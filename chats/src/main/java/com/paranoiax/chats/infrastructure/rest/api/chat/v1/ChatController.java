package com.paranoiax.chats.infrastructure.rest.api.chat.v1;

import com.paranoiax.chats.application.ports.in.chat.addParticipant.AddParticipantsToChatUseCase;
import com.paranoiax.chats.application.ports.in.chat.create.CreateChatUseCase;
import com.paranoiax.chats.application.ports.in.chat.findAll.FindChatsByUserIdQuery;
import com.paranoiax.chats.application.ports.in.chat.findAll.FindChatsUseCase;
import com.paranoiax.chats.application.ports.in.invite.create.CreateInviteCommand;
import com.paranoiax.chats.application.ports.in.invite.create.CreateInviteUseCase;
import com.paranoiax.chats.application.ports.in.invite.use.UseInviteCommand;
import com.paranoiax.chats.application.ports.in.invite.use.UseInviteUseCase;
import com.paranoiax.chats.domain.models.chat.ChatId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/chats")
public class ChatController {
    private final CreateChatUseCase createChatUseCase;
    private final FindChatsUseCase findChatsUseCase;
    private final AddParticipantsToChatUseCase addParticipantsToChatUseCase;
    private final CreateInviteUseCase createInviteUseCase;
    private final UseInviteUseCase useInviteUseCase;

    @PostMapping
    public ResponseEntity<ChatIdResponse> create(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestBody @Valid CreateChatRequest request,
            @AuthenticationPrincipal UUID principalId
    ) {
        ChatId chatId = createChatUseCase.execute(request.toCommand(principalId, idempotencyKey));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ChatIdResponse(chatId.value().toString()));
    }

    @GetMapping
    public ResponseEntity<List<ChatResponse>> getAll(
            @AuthenticationPrincipal UUID principalId
    ) {
        return ResponseEntity.ok(
                findChatsUseCase.execute(new FindChatsByUserIdQuery(principalId)).stream()
                        .map(ChatResponse::from)
                        .collect(Collectors.toList())
        );
    }

    @PostMapping("/{chat_id}/participants")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addParticipant(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestBody @Valid AddParticipantsToChatRequest request,
            @AuthenticationPrincipal UUID principalId,
            @PathVariable("chat_id") UUID chatId
    ) {
        addParticipantsToChatUseCase.execute(request.toCommand(principalId, chatId, idempotencyKey));
    }

    @PostMapping("/{chat_id}/invites")
    public ResponseEntity<InviteResponse> createInvite(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestBody @Valid CreateInviteRequest request,
            @AuthenticationPrincipal UUID principalId,
            @PathVariable("chat_id") UUID chatId
    ) {
        InviteResponse response = InviteResponse.from(createInviteUseCase.execute(new CreateInviteCommand(
                principalId,
                chatId,
                request.maxUses(),
                request.expiresAt(),
                idempotencyKey
        )));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/invites/{invite_id}/join")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void useInvite(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestBody @Valid UseInviteRequest request,
            @AuthenticationPrincipal UUID principalId,
            @PathVariable("invite_id") UUID inviteId
    ) {
        useInviteUseCase.execute(new UseInviteCommand(
                principalId,
                inviteId,
                request.deviceKeys().stream()
                        .map(ParticipantDeviceDetailsRequest::toDetails)
                        .collect(Collectors.toList()),
                idempotencyKey
        ));
    }

}

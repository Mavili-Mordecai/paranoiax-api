package com.paranoiax.chats.infrastructure.rest.api.participants.v1;

import com.paranoiax.chats.application.ports.in.participant.addParticipant.AddParticipantsToChatUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/chats")
public class ParticipantsController {
    private final AddParticipantsToChatUseCase addParticipantsToChatUseCase;

    @PostMapping("/{chat_id}/participants")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestBody @Valid AddParticipantsToChatRequest request,
            @AuthenticationPrincipal UUID principalId,
            @PathVariable("chat_id") UUID chatId
    ) {
        addParticipantsToChatUseCase.execute(request.toCommand(principalId, chatId, idempotencyKey));
    }
}

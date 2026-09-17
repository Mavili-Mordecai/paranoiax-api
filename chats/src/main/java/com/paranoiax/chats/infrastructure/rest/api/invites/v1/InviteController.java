package com.paranoiax.chats.infrastructure.rest.api.invites.v1;

import com.paranoiax.chats.application.ports.in.invite.create.CreateInviteCommand;
import com.paranoiax.chats.application.ports.in.invite.create.CreateInviteUseCase;
import com.paranoiax.chats.application.ports.in.invite.use.UseInviteCommand;
import com.paranoiax.chats.application.ports.in.invite.use.UseInviteUseCase;
import com.paranoiax.chats.infrastructure.rest.api.chats.v1.ParticipantDeviceDetailsRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/chats")
public class InviteController {
    private final CreateInviteUseCase createInviteUseCase;
    private final UseInviteUseCase useInviteUseCase;

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

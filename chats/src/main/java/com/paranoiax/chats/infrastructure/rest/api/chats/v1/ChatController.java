package com.paranoiax.chats.infrastructure.rest.api.chats.v1;

import com.paranoiax.chats.application.ports.in.chat.create.CreateChatUseCase;
import com.paranoiax.chats.application.ports.in.chat.findAll.FindChatsByUserIdQuery;
import com.paranoiax.chats.application.ports.in.chat.findAll.FindChatsUseCase;
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
}

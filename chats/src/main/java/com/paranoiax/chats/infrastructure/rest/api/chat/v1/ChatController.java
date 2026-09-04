package com.paranoiax.chats.infrastructure.rest.api.chat.v1;

import com.paranoiax.chats.infrastructure.config.security.CustomJwtAuthentication;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/chats")
public class ChatController {

    @PostMapping
    public ResponseEntity<ChatIdResponse> create(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestBody @Valid CreateChatRequest request,
            CustomJwtAuthentication authentication
    ) {
        throw new UnsupportedOperationException();
    }

    @GetMapping
    public ResponseEntity<List<ChatResponse>> getAll(
            @AuthenticationPrincipal UUID userId
    ) {
        throw new UnsupportedOperationException();
    }
}

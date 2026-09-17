package com.paranoiax.chats.infrastructure.rest.api.keys.v1;

import com.paranoiax.chats.application.ports.in.key.findAll.FindAllPendingKeysQuery;
import com.paranoiax.chats.application.ports.in.key.findAll.FindAllPendingKeysUseCase;
import com.paranoiax.chats.application.ports.in.key.findAll.PendingKeyDetails;
import com.paranoiax.chats.infrastructure.config.security.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/chats")
public class KeyController {
    private final FindAllPendingKeysUseCase findAllPendingKeysUseCase;

    @GetMapping("/keys/pending")
    public ResponseEntity<List<PendingKeyResponse>> getPendingKeys(
            JwtAuthentication authentication
    ) {
        List<PendingKeyDetails> keys = findAllPendingKeysUseCase.execute(new FindAllPendingKeysQuery(authentication.getDeviceId()));
        return ResponseEntity.ok(keys.stream().map(PendingKeyResponse::from).collect(Collectors.toList()));
    }
}

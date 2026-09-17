package com.paranoiax.chats.infrastructure.rest.api.keys.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/chats")
public class KeyController {

    @GetMapping("/keys/pending")
    public ResponseEntity<List<PendingKeyResponse>> getPendingKeys() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

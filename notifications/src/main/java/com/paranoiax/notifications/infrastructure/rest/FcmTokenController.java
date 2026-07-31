package com.paranoiax.notifications.infrastructure.rest;


import org.springframework.security.oauth2.jwt.Jwt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/notifications/tokens")
@RequiredArgsConstructor
public class FcmTokenController {

    @PutMapping
    public ResponseEntity<Void> registerToken(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody TokenRequest request){
        UUID userId = UUID.fromString(jwt.getSubject());

        log.info("Получен запрос на обновление FCM токена для пользователя: {}, устройство: {}",
                userId, request.deviceId());

        return ResponseEntity.ok().build();
    }


    public record TokenRequest(String deviceId, String token) {}
}

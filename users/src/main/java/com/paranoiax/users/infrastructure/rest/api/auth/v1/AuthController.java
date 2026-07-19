package com.paranoiax.users.infrastructure.rest.api.auth.v1;

import com.paranoiax.users.application.ports.in.auth.invite.InviteUserCommand;
import com.paranoiax.users.application.ports.in.auth.invite.InviteUserUseCase;
import com.paranoiax.users.domain.models.invite.Invite;
import com.paranoiax.users.domain.models.user.UserId;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/users")
public class AuthController {

    private final InviteUserUseCase inviteUserUseCase;
    private final String publicHost;
    private final String spkiPin;

    public AuthController(
            InviteUserUseCase inviteUserUseCase,
            @Value("${application.public-host}") String publicHost,
            @Value("${application.spki-pin}") String spkiPin
    ) {
        this.inviteUserUseCase = inviteUserUseCase;
        this.publicHost = publicHost;
        this.spkiPin = spkiPin;
    }

    @PostMapping("/invite")
    public ResponseEntity<InviteResponse> invite(@RequestHeader("Idempotency-Key") String idempotencyKey) {
        Invite invite = inviteUserUseCase.execute(InviteUserCommand.of(
                new UserId(UUID.randomUUID()),
                idempotencyKey
        ));
        return ResponseEntity.ok(InviteResponse.of(
                publicHost,
                invite.getRegistrationToken().value(),
                spkiPin
        ));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody RegisterRequest request
    ) {
        log.info("Registering user with idempotency key {}, {}", idempotencyKey, request.toString());
    }

    @PostMapping("/auth/challenge")
    public ResponseEntity<ChallengeResponse> challenge(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestHeader("Device-Id") String deviceId
    ) {
        return ResponseEntity.ok(new ChallengeResponse(UUID.randomUUID().toString(), 60L * 1000));
    }

    @PostMapping("/auth")
    public ResponseEntity<TokensResponse> authenticate(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestHeader("Device-Id") String deviceId,
            @Valid @RequestBody AuthRequest request
    ) {
        return ResponseEntity.ok(new TokensResponse("test-token", "test-token"));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<TokensResponse> refresh(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestHeader("Device-Id") String deviceId,
            @Valid @RequestBody RefreshRequest request
    ) {
        return ResponseEntity.ok(new TokensResponse("test-token", "test-token"));
    }
}
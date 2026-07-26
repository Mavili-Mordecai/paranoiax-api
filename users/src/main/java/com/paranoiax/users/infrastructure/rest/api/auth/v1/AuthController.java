package com.paranoiax.users.infrastructure.rest.api.auth.v1;

import com.paranoiax.users.application.ports.in.auth.TokenPair;
import com.paranoiax.users.application.ports.in.auth.challengeAuth.ChallengeAuthCommand;
import com.paranoiax.users.application.ports.in.auth.challengeAuth.ChallengeAuthUseCase;
import com.paranoiax.users.application.ports.in.auth.createChallenge.CreateChallengeCommand;
import com.paranoiax.users.application.ports.in.auth.createChallenge.CreateChallengeUseCase;
import com.paranoiax.users.application.ports.in.auth.invite.InviteUserCommand;
import com.paranoiax.users.application.ports.in.auth.invite.InviteUserUseCase;
import com.paranoiax.users.application.ports.in.auth.refreshTokens.RefreshTokensCommand;
import com.paranoiax.users.application.ports.in.auth.refreshTokens.RefreshTokensUseCase;
import com.paranoiax.users.application.ports.in.auth.register.RegisterUserUseCase;
import com.paranoiax.users.domain.models.challenge.Challenge;
import com.paranoiax.users.domain.models.device.*;
import com.paranoiax.users.domain.models.invite.Invite;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class AuthController {
    private final InviteUserUseCase inviteUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final CreateChallengeUseCase createChallengeUseCase;
    private final ChallengeAuthUseCase challengeAuthUseCase;
    private final RefreshTokensUseCase refreshTokensUseCase;

    @Value("${application.public-host}")
    private String publicHost;

    @Value("${application.spki-pin}")
    private String spkiPin;

    @PostMapping("/invite")
    public ResponseEntity<InviteResponse> invite(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @AuthenticationPrincipal UUID userId
    ) {
        Invite invite = inviteUserUseCase.execute(InviteUserCommand.of(
                userId,
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
        registerUserUseCase.execute(request.toCommand(idempotencyKey));
    }

    @PostMapping("/auth/challenge")
    public ResponseEntity<ChallengeResponse> challenge(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestHeader("Device-Id") UUID deviceId
    ) {
        Challenge challenge = createChallengeUseCase.execute(new CreateChallengeCommand(deviceId, idempotencyKey));
        return ResponseEntity.ok(ChallengeResponse.from(challenge));
    }

    @PostMapping("/auth")
    public ResponseEntity<TokenPairResponse> authenticate(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestHeader("Device-Id") UUID deviceId,
            @Valid @RequestBody AuthRequest request
    ) {
        TokenPair tokenPair = challengeAuthUseCase.execute(new ChallengeAuthCommand(
                deviceId,
                request.signature(),
                request.challenge(),
                idempotencyKey
        ));
        return ResponseEntity.ok(TokenPairResponse.from(tokenPair));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<TokenPairResponse> refresh(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody RefreshRequest request
    ) {
        TokenPair tokenPair = refreshTokensUseCase.execute(new RefreshTokensCommand(
                request.refreshToken(),
                idempotencyKey
        ));
        return ResponseEntity.ok(TokenPairResponse.from(tokenPair));
    }
}
package com.paranoiax.users.infrastructure.rest.api.recoveryPoint.v1;

import com.paranoiax.users.application.ports.in.recoveryPoint.challenge.CreateRecoveryPointChallengeCommand;
import com.paranoiax.users.application.ports.in.recoveryPoint.challenge.CreateRecoveryPointChallengeUseCase;
import com.paranoiax.users.application.ports.in.recoveryPoint.create.CreateRecoveryPointCommand;
import com.paranoiax.users.application.ports.in.recoveryPoint.create.CreateRecoveryPointUseCase;
import com.paranoiax.users.application.ports.in.recoveryPoint.delete.DeleteRecoveryPointCommand;
import com.paranoiax.users.application.ports.in.recoveryPoint.delete.DeleteRecoveryPointUseCase;
import com.paranoiax.users.application.ports.in.recoveryPoint.get.AccessRecoveryPointCommand;
import com.paranoiax.users.application.ports.in.recoveryPoint.get.AccessRecoveryPointUseCase;
import com.paranoiax.users.application.ports.in.recoveryPoint.get.RecoveryPointDetails;
import com.paranoiax.users.domain.models.challenge.Challenge;
import com.paranoiax.users.infrastructure.rest.api.auth.v1.ChallengeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/users/recovery-points")
@RequiredArgsConstructor
public class RecoveryPointController {
    private final CreateRecoveryPointUseCase createRecoveryPointUseCase;
    private final CreateRecoveryPointChallengeUseCase createRecoveryPointChallengeUseCase;
    private final AccessRecoveryPointUseCase accessRecoveryPointUseCase;
    private final DeleteRecoveryPointUseCase deleteRecoveryPointUseCase;

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @AuthenticationPrincipal UUID userId,
            @Valid @RequestBody CreateRecoveryPointRequest request
    ) {
         createRecoveryPointUseCase.execute(new CreateRecoveryPointCommand(
                 userId,
                 request.identityKey(),
                 request.encryptedData(),
                 idempotencyKey
         ));
    }

    @PostMapping("/challenge")
    public ResponseEntity<ChallengeResponse> challenge(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestHeader("Device-Id") UUID deviceId,
            @Valid @RequestBody CreateRecoveryPointChallengeRequest request
    ) {
        Challenge challenge = createRecoveryPointChallengeUseCase.execute(new CreateRecoveryPointChallengeCommand(
                deviceId,
                request.username(),
                idempotencyKey
        ));
        return ResponseEntity.ok(ChallengeResponse.from(challenge));
    }

    @PostMapping("/access")
    public ResponseEntity<RecoveryPointResponse> access(
            @RequestHeader("Device-Id") UUID deviceId,
            @Valid @RequestBody AccessRecoveryPointRequest request
    ) {
        RecoveryPointDetails recoveryPoint = accessRecoveryPointUseCase.execute(new AccessRecoveryPointCommand(
                request.challenge(),
                request.signature(),
                deviceId
        ));
        return ResponseEntity.ok(RecoveryPointResponse.from(recoveryPoint));
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal UUID userId
    ) {
        deleteRecoveryPointUseCase.execute(new DeleteRecoveryPointCommand(id, userId));
    }
}
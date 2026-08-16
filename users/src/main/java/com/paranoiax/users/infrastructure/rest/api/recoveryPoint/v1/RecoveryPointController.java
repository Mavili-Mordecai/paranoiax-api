package com.paranoiax.users.infrastructure.rest.api.recoveryPoint.v1;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/users/recovery-points")
public class RecoveryPointController {

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @AuthenticationPrincipal UUID userId,
            @Valid @RequestBody CreateRecoveryPointRequest request
    ) {
         throw new UnsupportedOperationException("Not supported yet.");
    }

    @PostMapping("/get")
    public ResponseEntity<RecoveryPointResponse> get(
            @AuthenticationPrincipal UUID userId,
            @Valid @RequestBody GetRecoveryPointRequest request
    ) {
        throw  new UnsupportedOperationException("Not supported yet.");
    }

    @DeleteMapping("/{id}")
    public void delete(
            @AuthenticationPrincipal UUID userId,
            @PathVariable("id") UUID id
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
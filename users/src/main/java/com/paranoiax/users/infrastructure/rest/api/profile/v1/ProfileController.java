package com.paranoiax.users.infrastructure.rest.api.profile.v1;

import com.paranoiax.users.application.ports.in.profile.update.UpdateProfileCommand;
import com.paranoiax.users.application.ports.in.profile.update.UpdateProfileUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class ProfileController {
    private final UpdateProfileUseCase updateProfileUseCase;

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
            @Valid @RequestBody UpdateProfileRequest request,
            @AuthenticationPrincipal UUID userId
    ) {
        updateProfileUseCase.execute(new UpdateProfileCommand(
                userId,
                request.username(),
                request.firstName(),
                request.lastName(),
                request.bio()
        ));
    }
}
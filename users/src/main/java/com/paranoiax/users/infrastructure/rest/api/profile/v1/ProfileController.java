package com.paranoiax.users.infrastructure.rest.api.profile.v1;

import com.paranoiax.users.application.ports.in.profile.getKeys.GetUserKeysQuery;
import com.paranoiax.users.application.ports.in.profile.getKeys.GetUserKeysUseCase;
import com.paranoiax.users.application.ports.in.profile.getKeys.GetUsersKeysQuery;
import com.paranoiax.users.application.ports.in.profile.getKeys.UserKeysResult;
import com.paranoiax.users.application.ports.in.profile.search.SearchUserQuery;
import com.paranoiax.users.application.ports.in.profile.search.SearchUserResult;
import com.paranoiax.users.application.ports.in.profile.search.SearchUserUseCase;
import com.paranoiax.users.application.ports.in.profile.update.UpdateProfileCommand;
import com.paranoiax.users.application.ports.in.profile.update.UpdateProfileUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class ProfileController {
    private final UpdateProfileUseCase updateProfileUseCase;
    private final SearchUserUseCase searchUserUseCase;
    private final GetUserKeysUseCase getUserKeysUseCase;

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
            @Valid @RequestBody UpdateProfileRequest request,
            @AuthenticationPrincipal UUID userId
    ) {
        updateProfileUseCase.execute(new UpdateProfileCommand(
                userId,
                request.username(),
                request.profile()
        ));
    }

    @GetMapping("/{username}")
    public ResponseEntity<SearchUserResponse> search(@PathVariable String username) {
        SearchUserResult searchResult = searchUserUseCase.execute(new SearchUserQuery(username));
        return ResponseEntity.ok(SearchUserResponse.from(searchResult));
    }

    @GetMapping("/{user_id}/keys")
    public ResponseEntity<UserKeysResponse> getUserKeys(@PathVariable("user_id") UUID userId) {
        UserKeysResult result = getUserKeysUseCase.execute(new GetUserKeysQuery(userId));
        return ResponseEntity.ok(UserKeysResponse.from(result));
    }

    @PostMapping("/keys")
    public ResponseEntity<List<UserKeysResponse>> getUsersKeys(@Valid @RequestBody GetUsersKeysRequest request) {
        List<UserKeysResult> result = getUserKeysUseCase.execute(new GetUsersKeysQuery(request.userIds()));
        return ResponseEntity.ok(result.stream().map(UserKeysResponse::from).collect(Collectors.toList()));
    }
}
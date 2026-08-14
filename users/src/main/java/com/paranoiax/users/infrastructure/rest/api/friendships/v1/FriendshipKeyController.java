package com.paranoiax.users.infrastructure.rest.api.friendships.v1;

import com.paranoiax.core_infra.rest.exceptions.PageableResponse;
import com.paranoiax.users.application.ports.in.friendship.deleteKeys.DeleteFriendshipKeysCommand;
import com.paranoiax.users.application.ports.in.friendship.deleteKeys.DeleteFriendshipKeysUseCase;
import com.paranoiax.users.application.ports.in.friendship.getKeys.FriendshipKeyResult;
import com.paranoiax.users.application.ports.in.friendship.getKeys.GetFriendshipKeysQuery;
import com.paranoiax.users.application.ports.in.friendship.getKeys.GetFriendshipKeysUseCase;
import com.paranoiax.users.infrastructure.config.security.JwtAuthentication;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/friendships/keys")
@RequiredArgsConstructor
public class FriendshipKeyController {
    private final GetFriendshipKeysUseCase getFriendshipKeysUseCase;
    private final DeleteFriendshipKeysUseCase deleteFriendshipKeysUseCase;

    /** Returns pending keys for friends (ACCEPTED status) for a specific device */
    @GetMapping
    public ResponseEntity<PageableResponse<FriendshipKeyResponse>> getFriendshipKeys(
            @Min(value = 250, message = "INVALID_LENGTH") @Max(value = 1000, message = "INVALID_LENGTH") @RequestParam("limit") Integer limit,
            @Min(value = 0, message = "INVALID_LENGTH") @RequestParam("offset") Integer offset,
            JwtAuthentication authentication
    ) {
        FriendshipKeyResult result = getFriendshipKeysUseCase.execute(new GetFriendshipKeysQuery(
                authentication.getDeviceId(),
                limit,
                offset
        ));
        return ResponseEntity.ok(new PageableResponse<>(
                result.data().stream().map(FriendshipKeyResponse::from).toList(),
                result.hasMore(),
                result.serverTimeInMillis()
        ));
    }

    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFriendshipKey(
            @RequestBody @Valid DeleteFriendshipKeysRequest request,
            JwtAuthentication authentication
    ) {
        deleteFriendshipKeysUseCase.execute(new DeleteFriendshipKeysCommand(
                authentication.getDeviceId(),
                request.ids()
        ));
    }
}
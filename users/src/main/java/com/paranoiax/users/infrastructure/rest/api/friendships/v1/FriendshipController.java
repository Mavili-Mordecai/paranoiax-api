package com.paranoiax.users.infrastructure.rest.api.friendships.v1;

import com.paranoiax.core_infra.rest.exceptions.PageableResponse;
import com.paranoiax.users.application.ports.in.friendship.accept.AcceptFriendshipCommand;
import com.paranoiax.users.application.ports.in.friendship.accept.AcceptFriendshipUseCase;
import com.paranoiax.users.application.ports.in.friendship.add.AddFriendshipCommand;
import com.paranoiax.users.application.ports.in.friendship.add.AddFriendshipUseCase;
import com.paranoiax.users.application.ports.in.friendship.add.FriendshipKeyInfo;
import com.paranoiax.users.application.ports.in.friendship.block.BlockFriendshipCommand;
import com.paranoiax.users.application.ports.in.friendship.block.BlockFriendshipUseCase;
import com.paranoiax.users.application.ports.in.friendship.delete.DeleteFriendshipCommand;
import com.paranoiax.users.application.ports.in.friendship.delete.DeleteFriendshipUseCase;
import com.paranoiax.users.application.ports.in.friendship.get.FriendshipResult;
import com.paranoiax.users.application.ports.in.friendship.get.GetFriendshipsQuery;
import com.paranoiax.users.application.ports.in.friendship.get.GetFriendshipsUseCase;
import com.paranoiax.users.application.ports.in.friendship.unblock.UnblockFriendshipCommand;
import com.paranoiax.users.application.ports.in.friendship.unblock.UnblockFriendshipUseCase;
import com.paranoiax.users.application.ports.in.friendship.update.UpdateFriendshipCommand;
import com.paranoiax.users.application.ports.in.friendship.update.UpdateFriendshipUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/friendships")
@RequiredArgsConstructor
public class FriendshipController {
    private final GetFriendshipsUseCase getFriendshipsUseCase;
    private final AddFriendshipUseCase addFriendshipUseCase;
    private final UpdateFriendshipUseCase updateFriendshipUseCase;
    private final AcceptFriendshipUseCase acceptFriendshipUseCase;
    private final BlockFriendshipUseCase blockFriendshipUseCase;
    private final UnblockFriendshipUseCase unblockFriendshipUseCase;
    private final DeleteFriendshipUseCase deleteFriendshipUseCase;

    @GetMapping
    public ResponseEntity<PageableResponse<FriendshipResponse>> getFriendships(
            @AuthenticationPrincipal UUID userId,
            @RequestParam(value = "updated_after", defaultValue = "0")
            @Size(message = "INVALID_LENGTH")
            Long updatedAfter,
            @RequestParam(value = "limit", defaultValue = "500")
            @Size(min = 250, max = 1000, message = "INVALID_LENGTH")
            Integer limit,
            @RequestParam(value = "offset", defaultValue = "0") @Size(message = "INVALID_LENGTH") Integer offset
    ) {
        FriendshipResult result = getFriendshipsUseCase.execute(new GetFriendshipsQuery(userId, updatedAfter, limit));
        return ResponseEntity.ok(new PageableResponse<>(
                result.data().stream().map(FriendshipResponse::from).toList(),
                result.hasMore(),
                result.serverTimeInMillis()
        ));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void add(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @AuthenticationPrincipal UUID userId,
            @Valid @RequestBody AddFriendshipRequest request
    ) {
        addFriendshipUseCase.execute(new AddFriendshipCommand(
                userId,
                request.friendId(),
                request.attributes(),
                request.keys().stream()
                        .map((key) -> new FriendshipKeyInfo(
                                key.deviceId(),
                                key.sharedKey()
                        ))
                        .collect(Collectors.toList()),
                idempotencyKey
        ));
    }

    @PatchMapping("/{id}")
    public void update(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal UUID userId,
            @Valid @RequestBody UpdateFriendshipRequest request
    ) {
        updateFriendshipUseCase.execute(new UpdateFriendshipCommand(
                id,
                userId,
                request.attributes(),
                idempotencyKey
        ));
    }

    @PostMapping("/{id}/accept")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void accept(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal UUID userId
    ) {
        acceptFriendshipUseCase.execute(new AcceptFriendshipCommand(
                id,
                userId,
                idempotencyKey
        ));
    }

    @PostMapping("/block")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void block(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestParam("friendId") UUID friendId,
            @AuthenticationPrincipal UUID userId
    ) {
        blockFriendshipUseCase.execute(new BlockFriendshipCommand(
                userId,
                friendId,
                idempotencyKey
        ));
    }

    @PostMapping("/{id}/unblock")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unblock(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal UUID userId
    ) {
        unblockFriendshipUseCase.execute(new UnblockFriendshipCommand(
                id,
                userId,
                idempotencyKey
        ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal UUID userId
    ) {
        deleteFriendshipUseCase.execute(new DeleteFriendshipCommand(id, userId));
    }
}
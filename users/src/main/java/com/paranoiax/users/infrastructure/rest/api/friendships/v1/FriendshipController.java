package com.paranoiax.users.infrastructure.rest.api.friendships.v1;

import com.paranoiax.core_infra.rest.exceptions.PageableResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/friendships")
public class FriendshipController {

    @GetMapping
    public ResponseEntity<PageableResponse<FriendshipResponse>> getFriendships(
            @AuthenticationPrincipal UUID userId,
            @RequestParam(value = "updated_after", defaultValue = "0") @Size() Long updatedAfter,
            @RequestParam(value = "limit", defaultValue = "500") @Size(min = 250, max = 1000) Integer limit,
            @RequestParam(value = "offset", defaultValue = "0") @Size() Integer offset
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void add(
            @AuthenticationPrincipal UUID userId,
            @Valid @RequestBody AddFriendshipRequest request
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PatchMapping("/{id}")
    public void update(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal UUID userId,
            @Valid @RequestBody UpdateFriendshipRequest request
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PostMapping("/{id}/accept")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void accept(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal UUID userId
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PostMapping("/{id}/block")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void block(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal UUID userId
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PostMapping("/{id}/unblock")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unblock(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal UUID userId
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal UUID userId
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
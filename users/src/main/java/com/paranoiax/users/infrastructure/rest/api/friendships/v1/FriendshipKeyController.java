package com.paranoiax.users.infrastructure.rest.api.friendships.v1;

import com.paranoiax.users.infrastructure.config.security.JwtAuthentication;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/friendships/keys")
public class FriendshipKeyController {

    /** Возвращает pending ключи для друзей (статус ACCEPTED) для конкретного девайса */
    @GetMapping
    public ResponseEntity<List<FriendshipKeyResponse>> getFriendshipKeys(
            @Size(min = 250, max = 1000) @RequestParam("limit") Integer limit,
            @Size() @RequestParam("offset") Integer offset,
            JwtAuthentication authentication
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFriendshipKey(
            @RequestBody @Valid DeleteFriendshipKeysRequest request,
            JwtAuthentication authentication
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
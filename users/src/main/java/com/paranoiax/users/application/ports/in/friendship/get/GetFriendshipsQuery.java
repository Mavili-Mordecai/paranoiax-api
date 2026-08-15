package com.paranoiax.users.application.ports.in.friendship.get;

import java.util.UUID;

public record GetFriendshipsQuery(
        UUID userId,
        Long updatedAfter,
        Integer limit,
        Integer offset
) {
}
package com.paranoiax.users.application.ports.in.profile.search;

import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.domain.models.user.*;

public record SearchUserResult(
        UserId userId,
        Username username,
        Profile profile
) {
    public static SearchUserResult from(User user) {
        return new SearchUserResult(
                user.getId(),
                user.getUsername(),
                user.getProfile()
        );
    }
}
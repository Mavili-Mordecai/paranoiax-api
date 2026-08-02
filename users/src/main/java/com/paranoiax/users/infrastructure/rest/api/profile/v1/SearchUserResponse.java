package com.paranoiax.users.infrastructure.rest.api.profile.v1;

import com.paranoiax.users.domain.models.user.User;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SearchUserResponse(
        UUID userId,
        String username,
        AvatarInfo avatar
) {
    public static SearchUserResponse from(User user) {
        AvatarInfo avatarInfo = null;
        if (user.getAvatar() != null) {
            avatarInfo = new AvatarInfo(
                    user.getAvatar().getSmall().value(),
                    user.getAvatar().getMedium().value(),
                    user.getAvatar().getLarge().value()
            );
        }

        return new SearchUserResponse(
                user.getId().value(),
                user.getUsername().value(),
                avatarInfo
        );
    }
}
package com.paranoiax.users.infrastructure.rest.api.profile.v1;

import com.paranoiax.users.application.ports.in.profile.search.SearchUserResult;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SearchUserResponse(
        UUID userId,
        String username,
        AvatarInfo avatar
) {
    public static SearchUserResponse from(SearchUserResult searchResult) {
        AvatarInfo avatarInfo = null;
        if (searchResult.avatar() != null) {
            avatarInfo = new AvatarInfo(
                    searchResult.avatar().getSmall().value(),
                    searchResult.avatar().getMedium().value(),
                    searchResult.avatar().getLarge().value()
            );
        }

        return new SearchUserResponse(
                searchResult.userId().value(),
                searchResult.username().value(),
                avatarInfo
        );
    }
}
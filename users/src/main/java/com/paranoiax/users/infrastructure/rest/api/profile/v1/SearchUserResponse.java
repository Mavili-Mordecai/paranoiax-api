package com.paranoiax.users.infrastructure.rest.api.profile.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.paranoiax.users.application.ports.in.profile.search.SearchUserResult;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SearchUserResponse(
        UUID userId,
        String username,
        ProfileInfo profile
) {
    public static SearchUserResponse from(SearchUserResult searchResult) {
        ProfileInfo profileInfo = null;

        if (searchResult.profile() != null && searchResult.profile().data() != null && !searchResult.profile().data().isBlank()) {
            profileInfo = new ProfileInfo(
                    searchResult.profile().data(),
                    searchResult.profile().version()
            );
        }

        return new SearchUserResponse(
                searchResult.userId().value(),
                searchResult.username().value(),
                profileInfo
        );
    }
}
package com.paranoiax.users.application.ports.in.profile.search;

import com.paranoiax.users.domain.models.user.User;

public interface SearchUserUseCase {
    User execute(SearchUserQuery query);
}
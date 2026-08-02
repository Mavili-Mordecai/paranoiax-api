package com.paranoiax.users.application.services.profile;

import com.paranoiax.users.application.ports.in.profile.search.SearchUserQuery;
import com.paranoiax.users.application.ports.in.profile.search.SearchUserUseCase;
import com.paranoiax.users.application.ports.out.UserPort;
import com.paranoiax.users.domain.exceptions.NotFoundException;
import com.paranoiax.users.domain.models.user.User;
import com.paranoiax.users.domain.models.user.Username;

public class SearchUserService implements SearchUserUseCase {
    private final UserPort userPort;

    public SearchUserService(UserPort userPort) {
        this.userPort = userPort;
    }

    @Override
    public User execute(SearchUserQuery query) {
        return userPort.findByUsername(new Username(query.username())).orElseThrow(() -> new NotFoundException("User"));
    }
}
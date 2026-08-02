package com.paranoiax.users.application.ports.in.profile.search;


public interface SearchUserUseCase {
    SearchUserResult execute(SearchUserQuery query);
}
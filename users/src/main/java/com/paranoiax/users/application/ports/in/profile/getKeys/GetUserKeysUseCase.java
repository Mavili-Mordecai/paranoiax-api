package com.paranoiax.users.application.ports.in.profile.getKeys;

import java.util.List;

public interface GetUserKeysUseCase {
    UserKeysResult execute(GetUserKeysQuery query);
    List<UserKeysResult> execute(GetUsersKeysQuery query);
}
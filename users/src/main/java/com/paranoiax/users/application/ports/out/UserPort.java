package com.paranoiax.users.application.ports.out;

import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.users.domain.models.user.User;
import com.paranoiax.users.domain.models.user.Username;

import java.util.*;

public interface UserPort {
    User insert(User user);
    User update(User user);
    Optional<User> findById(UserId userId);
    Optional<User> findByUsername(Username username);
    List<User> findByIdIn(Collection<UUID> ids);
}
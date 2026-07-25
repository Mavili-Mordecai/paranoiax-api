package com.paranoiax.users.application.ports.out;

import com.paranoiax.users.domain.models.user.User;

public interface UserPort {
    User insert(User user);
    User update(User user);
}
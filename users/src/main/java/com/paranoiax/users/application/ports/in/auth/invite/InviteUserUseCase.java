package com.paranoiax.users.application.ports.in.auth.invite;

import com.paranoiax.users.domain.models.invite.Invite;

public interface InviteUserUseCase {
    Invite execute(InviteUserCommand command);
}

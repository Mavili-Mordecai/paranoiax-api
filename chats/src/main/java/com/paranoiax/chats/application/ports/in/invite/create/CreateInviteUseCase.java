package com.paranoiax.chats.application.ports.in.invite.create;

public interface CreateInviteUseCase {
    InviteDetails execute(CreateInviteCommand command);
}

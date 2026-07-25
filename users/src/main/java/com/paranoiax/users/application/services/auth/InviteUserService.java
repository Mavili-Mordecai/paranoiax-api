package com.paranoiax.users.application.services.auth;

import com.paranoiax.users.application.ports.in.auth.invite.InviteUserCommand;
import com.paranoiax.users.application.ports.in.auth.invite.InviteUserUseCase;
import com.paranoiax.users.application.ports.out.InvitePort;
import com.paranoiax.users.application.ports.out.TokenGenerator;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.domain.models.invite.Invite;
import com.paranoiax.users.domain.models.user.UserId;

import java.time.Duration;

public class InviteUserService implements InviteUserUseCase {
    private final InvitePort invitePort;
    private final TokenGenerator tokenGenerator;
    private final OperationExecutor executor;
    private final Duration lockTtl;
    private final Duration resultTtl;

    public InviteUserService(
            InvitePort invitePort,
            TokenGenerator tokenGenerator,
            OperationExecutor executor,
            Duration lockTtl,
            Duration resultTtl
    ) {
        this.invitePort = invitePort;
        this.tokenGenerator = tokenGenerator;
        this.executor = executor;
        this.lockTtl = lockTtl;
        this.resultTtl = resultTtl;
    }

    @Override
    public Invite execute(InviteUserCommand command) {
        return executor.execute(command.operationId(), Invite.class, lockTtl, resultTtl, () -> {
            Invite invite = Invite.create(new UserId(command.userId()), tokenGenerator.generate(), resultTtl);
            return invitePort.save(invite, resultTtl);
        });
    }
}
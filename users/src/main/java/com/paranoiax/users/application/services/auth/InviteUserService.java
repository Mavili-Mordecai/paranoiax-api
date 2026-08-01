package com.paranoiax.users.application.services.auth;

import com.paranoiax.users.application.ports.in.auth.invite.InviteUserCommand;
import com.paranoiax.users.application.ports.in.auth.invite.InviteUserUseCase;
import com.paranoiax.users.application.ports.out.InvitePort;
import com.paranoiax.users.application.ports.out.crypto.TokenGenerator;
import com.paranoiax.users.application.ports.out.UserPort;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.domain.exceptions.NotFoundException;
import com.paranoiax.users.domain.models.invite.Invite;
import com.paranoiax.users.domain.models.invite.RegistrationToken;
import com.paranoiax.users.domain.models.user.User;
import com.paranoiax.users.domain.models.user.UserId;

import java.time.Duration;

public class InviteUserService implements InviteUserUseCase {
    private final InvitePort invitePort;
    private final UserPort userPort;
    private final TokenGenerator tokenGenerator;
    private final OperationExecutor executor;
    private final Duration lockTtl;
    private final Duration resultTtl;
    private final int tokenSize;

    public InviteUserService(
            InvitePort invitePort,
            UserPort userPort,
            TokenGenerator tokenGenerator,
            OperationExecutor executor,
            Duration lockTtl,
            Duration resultTtl,
            int tokenSize
    ) {
        this.invitePort = invitePort;
        this.userPort = userPort;
        this.tokenGenerator = tokenGenerator;
        this.executor = executor;
        this.lockTtl = lockTtl;
        this.resultTtl = resultTtl;
        this.tokenSize = tokenSize;
    }

    @Override
    public Invite execute(InviteUserCommand command) {
        return executor.execute(command, Invite.class, lockTtl, resultTtl, () -> {
            User user = userPort.findById(new UserId(command.userId()))
                    .orElseThrow(() -> new NotFoundException("User"));

            Invite invite = Invite.create(
                    user.getId(),
                    new RegistrationToken(tokenGenerator.generate(tokenSize)),
                    resultTtl
            );
            return invitePort.save(invite, resultTtl);
        });
    }
}
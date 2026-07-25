package com.paranoiax.users.application.services.auth;

import com.paranoiax.users.application.ports.in.auth.register.RegisterUserCommand;
import com.paranoiax.users.application.ports.in.auth.register.RegisterUserUseCase;
import com.paranoiax.users.application.ports.out.*;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.domain.exceptions.ExpiredException;
import com.paranoiax.users.domain.exceptions.NotFoundException;
import com.paranoiax.users.domain.models.EncryptionKey;
import com.paranoiax.users.domain.models.IdentityKey;
import com.paranoiax.users.domain.models.device.*;
import com.paranoiax.users.domain.models.invite.Invite;
import com.paranoiax.users.domain.models.user.User;
import com.paranoiax.users.domain.models.user.Username;

import java.time.Duration;

public class RegisterUserService implements RegisterUserUseCase {
    private final UserPort userPort;
    private final DevicePort devicePort;
    private final InvitePort invitePort;
    private final OperationExecutor executor;
    private final Duration lockTtl;
    private final Duration resultTtl;

    public RegisterUserService(
            UserPort userPort,
            DevicePort devicePort,
            InvitePort invitePort,
            OperationExecutor executor,
            Duration lockTtl,
            Duration resultTtl
    ) {
        this.userPort = userPort;
        this.devicePort = devicePort;
        this.invitePort = invitePort;
        this.executor = executor;
        this.lockTtl = lockTtl;
        this.resultTtl = resultTtl;
    }

    @Override
    public void execute(RegisterUserCommand command) {
        executor.execute(command.operationId(), User.class, lockTtl, resultTtl, () -> {
            Invite invite = invitePort.findByToken(command.inviteToken()).orElseThrow(() -> new NotFoundException("Invite token"));

            if (invite.isExpired()) {
                throw new ExpiredException("Invite token");
            }

            User createdUser = userPort.insert(User.create(
                    new Username(command.username()),
                    invite.getUserId(),
                    new IdentityKey(command.identityKey())
            ));

            devicePort.insert(Device.create(
                    new DeviceId(command.device().id()),
                    createdUser.getId(),
                    new DeviceName(command.device().name()),
                    DeviceType.valueOf(command.device().type().toUpperCase()),
                    new IdentityKey(command.device().identityKey()),
                    new EncryptionKey(command.device().encryptionKey()),
                    new DeviceSignature(command.device().deviceSignature())
            ));

            return createdUser;
        });
    }
}
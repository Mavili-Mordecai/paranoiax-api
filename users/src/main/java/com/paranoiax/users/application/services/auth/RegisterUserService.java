package com.paranoiax.users.application.services.auth;

import com.paranoiax.users.application.ports.in.auth.register.RegisterUserCommand;
import com.paranoiax.users.application.ports.in.auth.register.RegisterUserUseCase;
import com.paranoiax.users.application.ports.out.*;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.domain.exceptions.ExpiredException;
import com.paranoiax.users.domain.exceptions.MissingRequiredFieldException;
import com.paranoiax.users.domain.exceptions.NotFoundException;
import com.paranoiax.users.domain.exceptions.RevokedException;
import com.paranoiax.users.domain.models.EncryptionKey;
import com.paranoiax.users.domain.models.IdentityKey;
import com.paranoiax.users.domain.models.device.*;
import com.paranoiax.users.domain.models.invite.Invite;
import com.paranoiax.users.domain.models.user.User;
import com.paranoiax.users.domain.models.user.UserId;
import com.paranoiax.users.domain.models.user.UserType;
import com.paranoiax.users.domain.models.user.Username;

import java.time.Duration;

public class RegisterUserService implements RegisterUserUseCase {
    private final UserPort userPort;
    private final DevicePort devicePort;
    private final InvitePort invitePort;
    private final OperationExecutor executor;
    private final boolean isInviteOnly;
    private final Duration lockTtl;
    private final Duration resultTtl;

    public RegisterUserService(
            UserPort userPort,
            DevicePort devicePort,
            InvitePort invitePort,
            OperationExecutor executor,
            boolean isInviteOnly,
            Duration lockTtl,
            Duration resultTtl
    ) {
        this.userPort = userPort;
        this.devicePort = devicePort;
        this.invitePort = invitePort;
        this.executor = executor;
        this.isInviteOnly = isInviteOnly;
        this.lockTtl = lockTtl;
        this.resultTtl = resultTtl;
    }

    @Override
    public void execute(RegisterUserCommand command) {
        executor.execute(command, User.class, lockTtl, resultTtl, () -> {
            Invite invite = null;
            UserId invitedById = null;

            if (command.inviteToken() != null && !command.inviteToken().isBlank()) {
                invite = invitePort.findByToken(command.inviteToken()).orElseThrow(() -> new NotFoundException("Invite token"));

                if (invite.isExpired()) {
                    throw new ExpiredException("Invite token");
                }

                invitedById = invite.getUserId();
            }

            if (isInviteOnly && invitedById == null) {
                throw new MissingRequiredFieldException("inviteToken");
            }

            User createdUser = userPort.insert(User.create(
                    new Username(command.username()),
                    UserType.USER,
                    invitedById,
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

            if (invite != null && !invitePort.delete(invite)) {
                throw new RevokedException("Invite");
            }

            return createdUser;
        });
    }
}
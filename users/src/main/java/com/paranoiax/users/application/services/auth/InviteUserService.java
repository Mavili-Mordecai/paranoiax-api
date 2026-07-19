package com.paranoiax.users.application.services.auth;

import com.paranoiax.users.application.ports.in.auth.invite.InviteUserCommand;
import com.paranoiax.users.application.ports.in.auth.invite.InviteUserUseCase;
import com.paranoiax.users.application.ports.out.InvitePort;
import com.paranoiax.users.application.ports.out.OperationResultPort;
import com.paranoiax.users.application.ports.out.TokenGenerator;
import com.paranoiax.users.domain.exceptions.DomainErrorCode;
import com.paranoiax.users.domain.exceptions.DomainException;
import com.paranoiax.users.domain.models.invite.Invite;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

public class InviteUserService implements InviteUserUseCase {
    private final InvitePort invitePort;
    private final OperationResultPort operationResultPort;
    private final TokenGenerator tokenGenerator;
    private final Duration lockTtl;
    private final Duration tokenTtl;

    public InviteUserService(
            InvitePort invitePort,
            OperationResultPort operationResultPort,
            TokenGenerator tokenGenerator,
            Long lockTtl,
            Long tokenTtl
    ) {
        this.invitePort = invitePort;
        this.operationResultPort = operationResultPort;
        this.tokenGenerator = tokenGenerator;
        this.lockTtl = Duration.ofSeconds(lockTtl);
        this.tokenTtl = Duration.ofSeconds(tokenTtl);
    }

    @Override
    public Invite execute(InviteUserCommand command) {
        Optional<Invite> savedInvite = operationResultPort.findResult(command.operationId(), Invite.class);
        if (savedInvite.isPresent()) {
            return savedInvite.get();
        }

        boolean isLock = operationResultPort.tryLock(command.operationId(), lockTtl);

        if (!isLock) {
            throw new DomainException(
                    DomainErrorCode.LOCK_ACQUISITION_FAILED,
                    Map.of("operationId", command.operationId()),
                    String.format(DomainErrorCode.LOCK_ACQUISITION_FAILED.getDefaultMessage(), command.operationId())
            );
        }

        Invite invite = Invite.create(command.userId(), tokenGenerator.generate(), tokenTtl);
        invite = invitePort.save(invite, tokenTtl);

        operationResultPort.saveResult(command.operationId(), invite, tokenTtl);

        return invite;
    }
}

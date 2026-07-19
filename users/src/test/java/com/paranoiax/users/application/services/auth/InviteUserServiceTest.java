package com.paranoiax.users.application.services.auth;

import com.paranoiax.users.application.ports.in.auth.invite.InviteUserCommand;
import com.paranoiax.users.application.ports.out.InvitePort;
import com.paranoiax.users.application.ports.out.OperationResultPort;
import com.paranoiax.users.application.ports.out.TokenGenerator;
import com.paranoiax.users.domain.exceptions.DomainErrorCode;
import com.paranoiax.users.domain.exceptions.DomainException;
import com.paranoiax.users.domain.models.invite.Invite;
import com.paranoiax.users.domain.models.invite.RegistrationToken;
import com.paranoiax.users.domain.models.user.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InviteUserServiceTest {

    private static final long LOCK_TTL_SECONDS = 5L;
    private static final long TOKEN_TTL_SECONDS = 900L;
    private static final String OPERATION_ID = "idem-key-1";

    @Mock
    private InvitePort invitePort;
    @Mock
    private OperationResultPort operationResultPort;
    @Mock
    private TokenGenerator tokenGenerator;

    private InviteUserService service;

    private final UserId userId = new UserId(UUID.randomUUID());
    private final InviteUserCommand command = InviteUserCommand.of(userId, OPERATION_ID);

    @BeforeEach
    void setUp() {
        service = new InviteUserService(
                invitePort,
                operationResultPort,
                tokenGenerator,
                LOCK_TTL_SECONDS,
                TOKEN_TTL_SECONDS
        );
    }

    @Test
    @DisplayName("returns the stored result without locking or regenerating when the operation was already processed")
    void returnsCachedResultOnIdempotentRetry() {
        Invite cached = Invite.create(userId, new RegistrationToken("cached"), Duration.ofSeconds(TOKEN_TTL_SECONDS));
        when(operationResultPort.findResult(OPERATION_ID, Invite.class)).thenReturn(Optional.of(cached));

        Invite result = service.execute(command);

        assertSame(cached, result);
        verify(operationResultPort, never()).tryLock(any(), any());
        verify(operationResultPort, never()).saveResult(any(), any(), any());
        verifyNoInteractions(invitePort, tokenGenerator);
    }

    @Test
    @DisplayName("creates, persists and caches a fresh invite when none exists yet")
    void createsAndPersistsFreshInvite() {
        when(operationResultPort.findResult(OPERATION_ID, Invite.class)).thenReturn(Optional.empty());
        when(operationResultPort.tryLock(OPERATION_ID, Duration.ofSeconds(LOCK_TTL_SECONDS))).thenReturn(true);
        when(tokenGenerator.generate()).thenReturn(new RegistrationToken("generated-token"));
        Invite persisted = Invite.create(userId, new RegistrationToken("generated-token"), Duration.ofSeconds(TOKEN_TTL_SECONDS));
        when(invitePort.save(any(Invite.class), eq(Duration.ofSeconds(TOKEN_TTL_SECONDS)))).thenReturn(persisted);

        Invite result = service.execute(command);

        assertSame(persisted, result);

        ArgumentCaptor<Invite> savedInvite = ArgumentCaptor.forClass(Invite.class);
        verify(invitePort).save(savedInvite.capture(), eq(Duration.ofSeconds(TOKEN_TTL_SECONDS)));
        assertSame(userId, savedInvite.getValue().getUserId());
        assertEquals("generated-token", savedInvite.getValue().getRegistrationToken().value());

        verify(operationResultPort).saveResult(OPERATION_ID, persisted, Duration.ofSeconds(TOKEN_TTL_SECONDS));
    }

    @Test
    @DisplayName("acquires the lock with the configured lock TTL")
    void acquiresLockWithConfiguredTtl() {
        when(operationResultPort.findResult(OPERATION_ID, Invite.class)).thenReturn(Optional.empty());
        when(operationResultPort.tryLock(any(), any())).thenReturn(true);
        when(tokenGenerator.generate()).thenReturn(new RegistrationToken("token"));
        when(invitePort.save(any(Invite.class), any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.execute(command);

        verify(operationResultPort).tryLock(OPERATION_ID, Duration.ofSeconds(LOCK_TTL_SECONDS));
    }

    @Test
    @DisplayName("throws LOCK_ACQUISITION_FAILED and touches nothing else when the lock cannot be acquired")
    void failsWhenLockNotAcquired() {
        when(operationResultPort.findResult(OPERATION_ID, Invite.class)).thenReturn(Optional.empty());
        when(operationResultPort.tryLock(OPERATION_ID, Duration.ofSeconds(LOCK_TTL_SECONDS))).thenReturn(false);

        DomainException exception = assertThrows(DomainException.class, () -> service.execute(command));

        assertEquals(DomainErrorCode.LOCK_ACQUISITION_FAILED, exception.getCode());
        assertEquals(OPERATION_ID, exception.getArgs().get("operationId"));
        verify(operationResultPort, never()).saveResult(any(), any(), any());
        verifyNoInteractions(invitePort, tokenGenerator);
    }
}

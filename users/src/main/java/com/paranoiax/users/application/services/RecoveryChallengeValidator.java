package com.paranoiax.users.application.services;

import com.paranoiax.core.domain.exceptions.AccessDeniedException;
import com.paranoiax.core.domain.exceptions.ExpiredException;
import com.paranoiax.core.domain.exceptions.InvalidChallengeTypeException;
import com.paranoiax.core.domain.exceptions.InvalidSignatureException;
import com.paranoiax.core.application.ports.out.CanonicalizerPort;
import com.paranoiax.core.application.ports.out.crypto.SignatureVerifierPort;
import com.paranoiax.core.domain.IdentityKey;
import com.paranoiax.users.domain.models.challenge.Challenge;
import com.paranoiax.users.domain.models.challenge.ChallengeType;
import com.paranoiax.users.domain.models.device.Device;
import com.paranoiax.users.domain.models.recoveryPoint.RecoveryPoint;

import java.util.Base64;
import java.util.Map;
import java.util.UUID;

public class RecoveryChallengeValidator {
    private final CanonicalizerPort canonicalizerPort;
    private final SignatureVerifierPort verifierPort;

    public RecoveryChallengeValidator(CanonicalizerPort canonicalizerPort, SignatureVerifierPort verifierPort) {
        this.canonicalizerPort = canonicalizerPort;
        this.verifierPort = verifierPort;
    }

    public void validateRecoveryPoint(String signature, RecoveryPoint recoveryPoint, Challenge challenge) {
        boolean verified = verifierPort.verify(
                recoveryPoint.getIdentityKey().value(),
                challenge.getChallenge().value(),
                signature
        );

        if (!verified) {
            throw new InvalidSignatureException("Challenge");
        }
    }

    public void validateChallenge(UUID deviceId, Challenge challenge) {
        if (challenge.getType() != ChallengeType.ACCOUNT_RECOVERY) {
            throw new InvalidChallengeTypeException(challenge.getType().name());
        }

        if (!deviceId.equals(challenge.getDeviceId().value())) {
            throw new AccessDeniedException();
        }

        if (challenge.isExpired()) {
            throw new ExpiredException("Challenge");
        }
    }

    public void validateDeviceSignature(
            IdentityKey identityKey,
            Device device
    ) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] deviceSignaturePayload = canonicalizerPort.canonicalize(Map.of(
                "device_id", device.getId().value().toString(),
                "identity_key", device.getIdentityKey().value(),
                "encryption_key", device.getEncryptionKey().value()
        ));

        boolean verified = verifierPort.verify(
                decoder.decode(identityKey.value()),
                deviceSignaturePayload,
                decoder.decode(device.getDeviceSignature().value())
        );

        if (!verified) {
            throw new InvalidSignatureException("Device");
        }
    }
}

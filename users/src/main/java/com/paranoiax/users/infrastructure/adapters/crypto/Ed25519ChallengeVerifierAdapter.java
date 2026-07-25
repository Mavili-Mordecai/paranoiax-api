package com.paranoiax.users.infrastructure.adapters.crypto;

import com.paranoiax.users.application.ports.out.ChallengeVerifierPort;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

@Component
public class Ed25519ChallengeVerifierAdapter implements ChallengeVerifierPort {
    @Override
    public boolean verify(byte[] publicKey, byte[] challenge, byte[] signature) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("Ed25519");
            PublicKey key = keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));

            Signature verifier = Signature.getInstance("Ed25519");
            verifier.initVerify(key);

            verifier.update(challenge);

            return verifier.verify(signature);
        } catch (Exception e) {
            return false;
        }
    }
}
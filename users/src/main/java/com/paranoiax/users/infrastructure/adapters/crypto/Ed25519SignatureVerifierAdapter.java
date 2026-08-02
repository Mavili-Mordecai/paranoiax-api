package com.paranoiax.users.infrastructure.adapters.crypto;

import com.paranoiax.users.application.ports.out.crypto.SignatureVerifierPort;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class Ed25519SignatureVerifierAdapter implements SignatureVerifierPort {
    @Override
    public boolean verify(String publicKey, String data, String signature) {
        try {
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
            byte[] dataBytes = Base64.getDecoder().decode(data);
            byte[] signatureBytes = Base64.getDecoder().decode(signature);
            return verify(publicKeyBytes, dataBytes, signatureBytes);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean verify(byte[] publicKey, byte[] data, byte[] signature) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("Ed25519");
            PublicKey key = keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));

            Signature verifier = Signature.getInstance("Ed25519");
            verifier.initVerify(key);

            verifier.update(data);

            return verifier.verify(signature);
        } catch (Exception e) {
            return false;
        }
    }
}
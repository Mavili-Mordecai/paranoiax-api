package com.paranoiax.users.application.ports.out.crypto;

public interface SignatureVerifierPort {
    boolean verify(String publicKey, String data, String signature);
    boolean verify(byte[] publicKey, byte[] data, byte[] signature);
}
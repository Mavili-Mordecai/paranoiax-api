package com.paranoiax.users.application.ports.out;

public interface ChallengeVerifierPort {
    boolean verify(byte[] publicKey, byte[] challenge, byte[] signature);
}
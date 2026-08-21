package com.paranoiax.users.application.ports.out.crypto;

public interface HashPort {
    String sha256Hex(byte[] payload);
    String sha256Hex(String payload);
}
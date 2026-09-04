package com.paranoiax.core.application.ports.out.crypto;

public interface HashPort {
    String sha256Hex(byte[] payload);
    String sha256Hex(String payload);
}
package com.paranoiax.core_infra.adapters.crypto;

import com.paranoiax.core.application.ports.out.crypto.HashPort;
import org.apache.commons.codec.digest.DigestUtils;

public class ApacheHashAdapter implements HashPort {
    @Override
    public String sha256Hex(byte[] payload) {
        return DigestUtils.sha256Hex(payload);
    }

    @Override
    public String sha256Hex(String payload) {
        return DigestUtils.sha256Hex(payload);
    }
}
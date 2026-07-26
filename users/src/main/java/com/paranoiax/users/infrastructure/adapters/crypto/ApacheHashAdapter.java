package com.paranoiax.users.infrastructure.adapters.crypto;

import com.paranoiax.users.application.ports.out.crypto.HashPort;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class ApacheHashAdapter implements HashPort {
    @Override
    public String sha256Hex(String payload) {
        return DigestUtils.sha256Hex(payload);
    }
}
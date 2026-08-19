package com.paranoiax.users.infrastructure.adapters;

import com.paranoiax.users.application.ports.out.CanonicalizerPort;
import lombok.RequiredArgsConstructor;
import org.erdtman.jcs.JsonCanonicalizer;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JsonCanonicalizerAdapter implements CanonicalizerPort {
    private final ObjectMapper objectMapper;

    @Override
    public byte[] canonicalize(Object payload) {
        try {
            String json = objectMapper.writeValueAsString(payload);
            JsonCanonicalizer jc = new JsonCanonicalizer(json);
            return jc.getEncodedUTF8();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

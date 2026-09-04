package com.paranoiax.core_infra.adapters;

import com.paranoiax.core.application.ports.out.CanonicalizerPort;
import org.erdtman.jcs.JsonCanonicalizer;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonCanonicalizerAdapter implements CanonicalizerPort {
    private final ObjectMapper objectMapper;

    public JsonCanonicalizerAdapter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

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

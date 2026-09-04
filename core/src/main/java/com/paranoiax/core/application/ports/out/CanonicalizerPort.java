package com.paranoiax.core.application.ports.out;

public interface CanonicalizerPort {
    byte[] canonicalize(Object payload);
}

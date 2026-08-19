package com.paranoiax.users.application.ports.out;

public interface CanonicalizerPort {
    byte[] canonicalize(Object payload);
}

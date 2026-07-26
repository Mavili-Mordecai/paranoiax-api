package com.paranoiax.users.application.ports.out.operationResult;

public interface OperationCommand {
    String getPayloadSignature();
    String operationId();
}
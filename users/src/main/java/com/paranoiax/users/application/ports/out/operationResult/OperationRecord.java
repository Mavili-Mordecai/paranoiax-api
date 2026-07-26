package com.paranoiax.users.application.ports.out.operationResult;

public class OperationRecord<T> {
    private String payloadSignature;
    private T result;

    public OperationRecord() {

    }

    public OperationRecord(String payloadSignature, T result) {
        this.payloadSignature = payloadSignature;
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getPayloadSignature() {
        return payloadSignature;
    }

    public void setPayloadSignature(String payloadSignature) {
        this.payloadSignature = payloadSignature;
    }
}

package com.tfm.microservices.sensor.exception;

public class UnauthorizedOwnerException extends RuntimeException {

    public UnauthorizedOwnerException(String msg) {
        super(msg);
    }
}
package com.tfm.microservices.plots.exception;

public class UnauthorizedOwnerException extends RuntimeException {

    public UnauthorizedOwnerException(String msg) {
        super(msg);
    }
}
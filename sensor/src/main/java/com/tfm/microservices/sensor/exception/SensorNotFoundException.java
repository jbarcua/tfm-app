package com.tfm.microservices.sensor.exception;

public class SensorNotFoundException extends RuntimeException {

    public SensorNotFoundException(String msg) {
        super(msg);
    }
}
package com.tfm.microservices.sensor.pojo.request;

import lombok.Data;

@Data
public class SensorRequest {
    private String owner;
    private String catastralReference;
    private double latitude;
    private double longitude;
    private String type;
}

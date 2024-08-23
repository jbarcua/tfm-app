package com.tfm.microservices.sensor.pojo.response;

import lombok.Data;

@Data
public class SensorResponse {
    private String sensorId;
    private String catastralReference;
    private double latitude;
    private double longitude;
    private String type;
}

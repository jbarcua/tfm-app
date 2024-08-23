package com.tfm.microservices.plots.pojos.request;

import lombok.Data;

@Data
public class SensorRequest {
    private String owner;
    private double latitude;
    private double longitude;
    private String type;
}

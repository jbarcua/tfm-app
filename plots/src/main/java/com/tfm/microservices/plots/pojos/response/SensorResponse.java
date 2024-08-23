package com.tfm.microservices.plots.pojos.response;

import lombok.Data;

@Data
public class SensorResponse {
    private String catastralReference;
    private double latitude;
    private double longitude;
    private String type;
}

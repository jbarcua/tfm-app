package com.tfm.microservices.plots.repository;

import lombok.Data;

@Data
public class Sensor {
    private double latitude;
    private double longitude;
    private String type;
}

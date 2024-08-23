package com.tfm.microservices.sensor.repository;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String sensorId;
    private String owner;
    private String catastralReference;
    private double latitude;
    private double longitude;
    private String type;
}
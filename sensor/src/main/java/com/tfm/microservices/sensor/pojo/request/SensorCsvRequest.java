package com.tfm.microservices.sensor.pojo.request;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class SensorCsvRequest {

    @CsvBindByPosition(position = 0)
    private String catastralReference;
    @CsvBindByPosition(position = 1)
    private double latitude;
    @CsvBindByPosition(position = 2)
    private double longitude;
    @CsvBindByPosition(position = 3)
    private String type;

}
package com.tfm.microservices.plots.pojos;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class SensorCsvRequest {

    @CsvBindByPosition(position = 0)
    private double latitude;
    @CsvBindByPosition(position = 1)
    private double longitude;
    @CsvBindByPosition(position = 2)
    private String type;
}
package com.tfm.microservices.plots.pojos.response;

import com.tfm.microservices.plots.pojos.FeatureCollection;
import com.tfm.microservices.plots.repository.Sensor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlotResponse {
    private String id;
    private String catastralReference;
    private FeatureCollectionResponse geoData;
    private List<SensorResponse> sensorList;
}

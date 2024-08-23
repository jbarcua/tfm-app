package com.tfm.microservices.plots.repository;

import com.tfm.microservices.plots.pojos.FeatureCollection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "layers")
public class Plot {

    @Id
    private String id;
    private String owner;
    private String catastralReference;
    private FeatureCollection geoData;
    private List<Sensor> sensorList;

}

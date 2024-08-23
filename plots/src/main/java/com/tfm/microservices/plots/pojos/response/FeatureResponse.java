package com.tfm.microservices.plots.pojos.response;
import lombok.Data;
import org.springframework.data.mongodb.core.geo.GeoJson;

import java.util.HashMap;

@Data
public class FeatureResponse {
    private String type;

    //@JsonSerialize(using = GeoJsonCustomSerializer.class)
    //@JsonDeserialize(using = GeoJsonCustomDeserializer.class)
    private GeometryResponse geometry;

    private HashMap<String,String> properties = new HashMap<>();
}

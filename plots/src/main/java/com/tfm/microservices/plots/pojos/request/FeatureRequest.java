package com.tfm.microservices.plots.pojos.request;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
public class FeatureRequest {
    private String type;

    //@JsonSerialize(using = GeoJsonCustomSerializer.class)
    //@JsonDeserialize(using = GeoJsonCustomDeserializer.class)
    private GeometryRequest geometry;

    private HashMap<String,String> properties = new HashMap<>();
}

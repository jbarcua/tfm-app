package com.tfm.microservices.plots.pojos;
import com.mongodb.client.model.geojson.GeoJsonObjectType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJson;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Feature {
    private String type;

    //@JsonSerialize(using = GeoJsonCustomSerializer.class)
    //@JsonDeserialize(using = GeoJsonCustomDeserializer.class)
    private GeoJson geometry;

    private HashMap<String,String> properties = new HashMap<>();
}

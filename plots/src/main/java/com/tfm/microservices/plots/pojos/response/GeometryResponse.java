package com.tfm.microservices.plots.pojos.response;

import lombok.Data;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.util.Pair;

import java.util.ArrayList;

@Data
public class GeometryResponse {
    private String type;
    private ArrayList<ArrayList<double[]>> coordinates;
}

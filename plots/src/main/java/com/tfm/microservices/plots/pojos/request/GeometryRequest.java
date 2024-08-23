package com.tfm.microservices.plots.pojos.request;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class GeometryRequest {
    private String type;
    private ArrayList<ArrayList<double[]>> coordinates;
}

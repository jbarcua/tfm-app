package com.tfm.microservices.plots.pojos.response;

import com.tfm.microservices.plots.pojos.Feature;
import lombok.Data;

@Data
public class FeatureCollectionResponse {
    private String type;
    private FeatureResponse[] features;
}

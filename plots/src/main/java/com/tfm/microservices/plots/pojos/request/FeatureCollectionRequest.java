package com.tfm.microservices.plots.pojos.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeatureCollectionRequest {
    private String type;
    private FeatureRequest[] features;
}

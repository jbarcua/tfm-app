package com.tfm.microservices.plots.pojos.request;

import lombok.Data;

@Data
public class CheckOwnerRequest {
    private String owner;
    private String catastralReference;
}

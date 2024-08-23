package com.tfm.microservices.sensor.pojo.request;

import lombok.Data;

@Data
public class CheckOwnerRequest {
    private String owner;
    private String catastralReference;
}

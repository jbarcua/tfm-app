package com.tfm.microservices.plots.feign;

import com.tfm.microservices.plots.pojos.request.SensorRequest;
import com.tfm.microservices.plots.pojos.response.SensorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="sensor", url="http://${APP_SENSOR_SERVICE}:8081")
public interface SensorClient {
 
    @RequestMapping(value = "/sensor", method = RequestMethod.GET)
    List<SensorResponse> getSensorByCatastralReferenceAndOwner(@RequestParam String owner, @RequestParam(required = false) String catastralReference);
}

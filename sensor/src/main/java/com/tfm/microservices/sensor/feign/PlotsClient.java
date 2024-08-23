package com.tfm.microservices.sensor.feign;

import com.tfm.microservices.sensor.pojo.request.CheckOwnerRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="plots", url="http://plots:8080")
public interface PlotsClient {
 
    @RequestMapping(name = "/check", method = RequestMethod.POST)
    String getPlotByCatastralReference(@RequestBody CheckOwnerRequest checkOwnerRequest);
}

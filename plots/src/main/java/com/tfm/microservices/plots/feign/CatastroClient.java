package com.tfm.microservices.plots.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="catastro", url="http://ovc.catastro.meh.es/INSPIRE/wfsCP.aspx")
public interface CatastroClient {
 
    @RequestMapping(method = RequestMethod.GET)
    String getPlotByCatastralReference(@RequestParam String service, @RequestParam String version, @RequestParam String request, @RequestParam String STOREDQUERIE_ID, @RequestParam String refcat, @RequestParam String srsname);
}
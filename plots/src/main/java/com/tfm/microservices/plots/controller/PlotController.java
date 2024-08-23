package com.tfm.microservices.plots.controller;

import com.tfm.microservices.plots.pojos.request.CheckOwnerRequest;
import com.tfm.microservices.plots.pojos.response.PlotResponse;
import com.tfm.microservices.plots.service.PlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("plots")
public class PlotController {

    @Autowired
    PlotService plotService;

    @GetMapping("/owner/{owner}")
    @ResponseBody
    public ResponseEntity<List<PlotResponse>> getPlotsByOwner(@PathVariable String owner) {
        List<PlotResponse> plots = plotService.getPlotsByOwner(owner);
        return ResponseEntity.ok(plots);
    }

    @GetMapping("/{catastralReference}")
    @ResponseBody
    public ResponseEntity<PlotResponse> getPlotsByCatastralReference(@PathVariable String catastralReference) {
        PlotResponse plot = plotService.getPlotsByCatastralReference(catastralReference);
        return ResponseEntity.ok(plot);
    }



    @PostMapping("/reference/{reference}")
    @ResponseBody
    public ResponseEntity<Void> addPlotByReference(@PathVariable String reference) {
        plotService.addPlotByCatastralReference(reference, "pepe");
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/file/{owner}")
    @ResponseBody
    public ResponseEntity<Void> addPlotByCSVReferences(@RequestPart MultipartFile file, @PathVariable String owner) {
        plotService.addPlotByCSVReferences(file, owner);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/check")
    @ResponseBody
    public ResponseEntity<Void> checkOwnerRequest(@RequestBody CheckOwnerRequest checkOwnerRequest) {
        plotService.checkAllowedUser(checkOwnerRequest);
        return ResponseEntity.noContent().build();
    }
}

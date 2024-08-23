package com.tfm.microservices.sensor.controller;

import com.tfm.microservices.sensor.pojo.request.SensorRequest;
import com.tfm.microservices.sensor.pojo.response.SensorResponse;
import com.tfm.microservices.sensor.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("sensor")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<Void> addSensor(@RequestBody SensorRequest sensorRequest) {
        sensorService.addSensor(sensorRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<SensorResponse>> getSensorList(@RequestParam String owner, @RequestParam(required = false) String catastralReference) {
        List<SensorResponse> sensorResponse = sensorService.getSensorByOwnerAndCatastralReference(owner, catastralReference);
        return ResponseEntity.ok(sensorResponse);
    }

    @GetMapping("/{sensorId}")
    @ResponseBody
    public ResponseEntity<SensorResponse> getSensorBySensorId(@PathVariable String sensorId) {
        SensorResponse sensorResponse = sensorService.getSensorBySensorId(sensorId);
        return ResponseEntity.ok(sensorResponse);
    }

    @PostMapping("/file/{owner}")
    @ResponseBody
    public ResponseEntity<Void> addSensorByCSV(@RequestPart MultipartFile file, @PathVariable String owner) {
        sensorService.addSensorByCSV(file, owner);
        return ResponseEntity.noContent().build();
    }
}

package com.tfm.microservices.sensor.service;

import com.tfm.microservices.sensor.pojo.request.SensorRequest;
import com.tfm.microservices.sensor.pojo.response.SensorResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SensorService {

    void addSensor(SensorRequest sensorRequest);

    void addSensorByCSV(MultipartFile file, String owner);

    List<SensorResponse> getSensorByOwnerAndCatastralReference(String owner, String catastralReference);

    SensorResponse getSensorBySensorId(String sensorId);
}

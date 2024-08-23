package com.tfm.microservices.plots.service;

import com.tfm.microservices.plots.pojos.request.CheckOwnerRequest;
import com.tfm.microservices.plots.pojos.request.SensorRequest;
import com.tfm.microservices.plots.pojos.response.PlotResponse;
import com.tfm.microservices.plots.pojos.response.SensorResponse;
import com.tfm.microservices.plots.repository.Plot;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PlotService {

    List<PlotResponse> getPlotsByOwner(String owner);

    PlotResponse getPlotsByCatastralReference(String catastralReference);

    void addPlotByCatastralReference(String reference, String owner);

    void addPlotByCSVReferences(MultipartFile file, String owner);

    void addSensor(String catastralReference, SensorRequest sensorRequest);

    List<SensorResponse> getSensorList(String catastralReference);

    void addSensorByCSV(MultipartFile file, String owner);

    void checkAllowedUser(CheckOwnerRequest checkOwnerRequest);
}

package com.tfm.microservices.plots.mapper;

import com.tfm.microservices.plots.pojos.SensorCsvRequest;
import com.tfm.microservices.plots.pojos.request.SensorRequest;
import com.tfm.microservices.plots.pojos.response.GeometryResponse;
import com.tfm.microservices.plots.pojos.response.PlotResponse;
import com.tfm.microservices.plots.pojos.response.SensorResponse;
import com.tfm.microservices.plots.repository.Plot;
import com.tfm.microservices.plots.repository.Sensor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface SensorMapper {
  SensorMapper MAPPER = Mappers.getMapper(SensorMapper.class);

  Sensor sensorRequestToSensor(SensorRequest sensorRequest);
  Sensor sensorCsvRequestToSensor(SensorCsvRequest sensorCsvRequest);
  List<Sensor> sensorCsvRequestListToSensorList(List<SensorCsvRequest> sensorCsvRequest);
  SensorResponse sensorToSensorResponse(Sensor sensor);
  List<SensorResponse> sensorListToSensorResponseList(List<Sensor> sensorRequest);
}
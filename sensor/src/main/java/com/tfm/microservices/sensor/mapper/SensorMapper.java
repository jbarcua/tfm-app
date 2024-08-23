package com.tfm.microservices.sensor.mapper;

import com.tfm.microservices.sensor.pojo.request.SensorCsvRequest;
import com.tfm.microservices.sensor.pojo.request.SensorRequest;
import com.tfm.microservices.sensor.pojo.response.SensorResponse;
import com.tfm.microservices.sensor.repository.Sensor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SensorMapper {
  SensorMapper MAPPER = Mappers.getMapper(SensorMapper.class);

  Sensor sensorRequestToSensor(SensorRequest sensorRequest);
  Sensor sensorCsvRequestToSensor(SensorCsvRequest sensorCsvRequest);
  List<Sensor> sensorCsvRequestListToSensorList(List<SensorCsvRequest> sensorCsvRequest);
  SensorResponse sensorToSensorResponse(Sensor sensor);
  List<SensorResponse> sensorListToSensorResponseList(List<Sensor> sensorRequest);
}
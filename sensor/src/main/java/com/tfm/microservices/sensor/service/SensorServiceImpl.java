package com.tfm.microservices.sensor.service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.tfm.microservices.sensor.exception.SensorNotFoundException;
import com.tfm.microservices.sensor.mapper.SensorMapper;
import com.tfm.microservices.sensor.pojo.request.SensorCsvRequest;
import com.tfm.microservices.sensor.pojo.request.SensorRequest;
import com.tfm.microservices.sensor.pojo.response.SensorResponse;
import com.tfm.microservices.sensor.repository.Sensor;
import com.tfm.microservices.sensor.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SensorServiceImpl implements SensorService {

    @Autowired
    SensorRepository sensorRepository;

    @Override
    public void addSensor(SensorRequest sensorRequest) {
        Sensor sensor = SensorMapper.MAPPER.sensorRequestToSensor(sensorRequest);
        sensor.setSensorId(UUID.randomUUID().toString());
        sensorRepository.save(sensor);
    }

    @Override
    public void addSensorByCSV(MultipartFile file, String owner) {
        try {
            Reader reader = new InputStreamReader(file.getInputStream());
            List<SensorCsvRequest> sensors = new CsvToBeanBuilder(reader)
                    .withType(SensorCsvRequest.class)
                    .build()
                    .parse();
            List<Sensor> sensorList = SensorMapper.MAPPER.sensorCsvRequestListToSensorList(sensors);
            for (Sensor sensor : sensorList) {
                sensorRepository.save(sensor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SensorResponse> getSensorByOwnerAndCatastralReference(String owner, String catastralReference) {
        List<Sensor> sensorList;
        if(catastralReference != null) {
           sensorList  = sensorRepository.findByOwnerAndCatastralReference(owner, catastralReference);
        } else {
            sensorList = sensorRepository.findByOwner(owner);
        }
        return SensorMapper.MAPPER.sensorListToSensorResponseList(sensorList);
    }

    @Override
    public SensorResponse getSensorBySensorId(String sensorId) {
            Optional<Sensor> optionalSensor = sensorRepository.findBySensorId(sensorId);
            Sensor sensor = optionalSensor.orElseThrow(() -> new SensorNotFoundException("Sensor " + sensorId + " not found"));
            return SensorMapper.MAPPER.sensorToSensorResponse(sensor);
    }
}

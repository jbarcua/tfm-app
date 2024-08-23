package com.tfm.microservices.sensor.repository;

import org.kolobok.annotation.FindWithOptionalParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {

    List<Sensor> findByOwner(String owner);
    List<Sensor> findByOwnerAndCatastralReference(String owner, String catastralReference);
    List<Sensor> findAll();
    Optional<Sensor> findBySensorId(String sensorId);
}
package com.tfm.microservices.plots.repository;

import org.springframework.data.geo.Distance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PlotRepository extends MongoRepository<Plot, String> {

  List<Plot> findByOwner(String owner);
  Optional<Plot> findByCatastralReference(String catastralReference);
  Optional<Plot> findByOwnerAndCatastralReference(String owner, String catastralReference);

}
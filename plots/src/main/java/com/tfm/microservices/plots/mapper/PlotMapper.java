package com.tfm.microservices.plots.mapper;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Polygon;
import com.tfm.microservices.plots.pojos.Feature;
import com.tfm.microservices.plots.pojos.response.FeatureResponse;
import com.tfm.microservices.plots.pojos.response.GeometryResponse;
import com.tfm.microservices.plots.pojos.response.PlotResponse;
import com.tfm.microservices.plots.repository.Plot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface PlotMapper {
  PlotMapper MAPPER = Mappers.getMapper(PlotMapper.class);
  @Mapping(target = "coordinates", qualifiedByName = "getCoordinates")
  GeometryResponse geoJsonToGeometryResponse(GeoJson geometry);
  PlotResponse plotToPlotResponse(Plot plot);
  List<PlotResponse> plotListToPlotListResponse(List<Plot> plot);

  @Named("getCoordinates")
  default ArrayList<ArrayList<double[]>> getCoordinates(Iterable coordinates) {
    Iterator iterator = coordinates.iterator();
    ArrayList<ArrayList<double[]>> result = new ArrayList<>();
    if(iterator.hasNext()) {
      GeoJsonLineString points = (GeoJsonLineString)iterator.next();
      ArrayList<double[]> coordinateList = points.getCoordinates().stream().map(point -> new double[]{point.getX(), point.getY()}).collect(Collectors.toCollection(ArrayList::new));
      result.add(coordinateList);
    }
    return result;
  }
}
package com.tfm.microservices.plots.service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.tfm.microservices.plots.exception.UnauthorizedOwnerException;
import com.tfm.microservices.plots.feign.CatastroClient;
import com.tfm.microservices.plots.feign.SensorClient;
import com.tfm.microservices.plots.mapper.PlotMapper;
import com.tfm.microservices.plots.mapper.SensorMapper;
import com.tfm.microservices.plots.pojos.Feature;
import com.tfm.microservices.plots.pojos.FeatureCollection;
import com.tfm.microservices.plots.pojos.SensorCsvRequest;
import com.tfm.microservices.plots.pojos.request.CheckOwnerRequest;
import com.tfm.microservices.plots.pojos.request.SensorRequest;
import com.tfm.microservices.plots.pojos.response.PlotResponse;
import com.tfm.microservices.plots.pojos.response.SensorResponse;
import com.tfm.microservices.plots.repository.Plot;
import com.tfm.microservices.plots.repository.PlotRepository;
import com.tfm.microservices.plots.repository.Sensor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlotServiceImpl implements PlotService {

    @Autowired
    PlotRepository plotRepository;

    @Autowired
    CatastroClient catastroClient;

    @Autowired
    SensorClient sensorClient;

    @Override
    public List<PlotResponse> getPlotsByOwner(String owner) {
        List<Plot> plots = plotRepository.findByOwner(owner);
        List<PlotResponse> plotResponseList = new ArrayList<>();
        for (Plot plot : plots) {
            PlotResponse plotResponse = PlotMapper.MAPPER.plotToPlotResponse(plot);
            List<SensorResponse> sensorList = sensorClient.getSensorByCatastralReferenceAndOwner(owner, plot.getCatastralReference());
            log.info("SERVICE - getPlotsByOwner(String owner)");
            plotResponse.setSensorList(sensorList);
            plotResponseList.add(plotResponse);
        }
        return plotResponseList;
    }

    @Override
    public PlotResponse getPlotsByCatastralReference(String catastralReference) {
        Plot plot = plotRepository.findByCatastralReference(catastralReference).get();
        PlotResponse plotResponse = PlotMapper.MAPPER.plotToPlotResponse(plot);
        List<SensorResponse> sensorList = sensorClient.getSensorByCatastralReferenceAndOwner("pepe", plot.getCatastralReference());
        plotResponse.setSensorList(sensorList);
        return plotResponse;
    }

    @Override
    public void addPlotByCatastralReference(String reference, String owner) {
        Plot plot = getPlotByCatastralReference(reference, owner);
        plotRepository.save(plot);
    }

    @Override
    public void addPlotByCSVReferences(MultipartFile file, String owner) {
        List<String> references = readReferencesFromFile(file);
        List<Plot> plots = new ArrayList<>();
        for (String reference : references) {
            Plot plot = getPlotByCatastralReference(reference, owner);
            plots.add(plot);
        }
        plotRepository.saveAll(plots);
    }

    @Override
    public void addSensor(String catastralReference, SensorRequest sensorRequest) {
        Plot plot = plotRepository.findByCatastralReference(catastralReference).get();
        Sensor sensor = SensorMapper.MAPPER.sensorRequestToSensor(sensorRequest);
        List<Sensor> sensorList = plot.getSensorList();
        if (sensorList == null) {
            sensorList = new ArrayList<>();
        }
        sensorList.add(sensor);
        plot.setSensorList(sensorList);
        plotRepository.save(plot);
    }

    @Override
    public List<SensorResponse> getSensorList(String catastralReference) {
        Plot plot = plotRepository.findByCatastralReference(catastralReference).orElse(new Plot());
        return SensorMapper.MAPPER.sensorListToSensorResponseList(plot.getSensorList());
    }

    @Override
    public void addSensorByCSV(MultipartFile file, String catastralReference) {
        try {
            Reader reader = new InputStreamReader(file.getInputStream());
            List<SensorCsvRequest> sensors = new CsvToBeanBuilder(reader)
                    .withType(SensorCsvRequest.class)
                    .build()
                    .parse();
            Plot plot = plotRepository.findByCatastralReference(catastralReference).get();
            List<Sensor> sensor = SensorMapper.MAPPER.sensorCsvRequestListToSensorList(sensors);
            List<Sensor> sensorList = plot.getSensorList();
            if (sensorList == null) {
                sensorList = new ArrayList<>();
            }
            sensorList.addAll(sensor);
            plot.setSensorList(sensorList);
            plotRepository.save(plot);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void checkAllowedUser(CheckOwnerRequest checkOwnerRequest) {
        plotRepository.findByOwnerAndCatastralReference(checkOwnerRequest.getOwner(), checkOwnerRequest.getCatastralReference()).orElseThrow(()
                -> new UnauthorizedOwnerException("User " + checkOwnerRequest.getOwner() + " hasn't permissions to edit this plot with reference " + checkOwnerRequest.getCatastralReference()));
    }

    private Plot getPlotByCatastralReference(String reference, String owner) {
        String response = catastroClient.getPlotByCatastralReference("wfs", "2", "getfeature", "GetParcel", reference, "EPSG::4326");
        String elements = StringUtils.substringBetween(response, "<gml:posList", "</gml:posList>");
        String elementsClean = StringUtils.substringAfter(elements, ">");
        List<String> list = Arrays.asList(elementsClean.split(" "));
        final AtomicInteger counter = new AtomicInteger(0);
        List<Point> listPoints = list.stream().collect(
                Collectors.groupingBy(item -> {
                    final int i = counter.getAndIncrement();
                    return (i % 2 == 0) ? i : i - 1;
                })).values().stream().map(a -> new Point((a.size() == 2 ? Double.parseDouble(a.get(1)) : null), Double.parseDouble(a.get(0)))).collect(Collectors.toList());
        System.out.println(listPoints);
        GeoJsonPolygon geoJsonPolygon = new GeoJsonPolygon(listPoints);
        Feature feature = Feature.builder().geometry(geoJsonPolygon).type("Feature").properties(new HashMap<>()).build();
        FeatureCollection featureCollection = FeatureCollection.builder().type("FeatureCollection").features(new Feature[]{feature}).build();
        return Plot.builder().owner(owner).catastralReference(reference).geoData(featureCollection).build();
    }

    private List<String> readReferencesFromFile(MultipartFile file) {
        BufferedReader br;
        List<String> result = new ArrayList<>();
        try {
            String line;
            InputStream is = file.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
}

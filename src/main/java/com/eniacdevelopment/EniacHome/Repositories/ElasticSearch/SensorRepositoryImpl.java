package com.eniacdevelopment.EniacHome.Repositories.ElasticSearch;

import com.eniacdevelopment.EniacHome.DataModel.Sensor.Sensor;
import com.eniacdevelopment.EniacHome.Repositories.Shared.SensorRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.SensorStatusRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.transport.TransportClient;

import javax.inject.Inject;

/**
 * Created by larsg on 1/6/2017.
 */
public class SensorRepositoryImpl extends RepositoryImpl<Sensor> implements SensorRepository {

    private final SensorStatusRepository sensorStatusRepository;

    @Inject
    public SensorRepositoryImpl(SensorStatusRepository sensorStatusRepository, TransportClient transportClient, ObjectMapper objectMapper) {
        super(transportClient, objectMapper, "sensor", Sensor.class);
        this.sensorStatusRepository = sensorStatusRepository;
    }

    @Override
    public Sensor get(String id) {
        Sensor sensor = super.get(id);
        sensor.SensorStatus = this.sensorStatusRepository.get(id);

        return sensor;
    }

    @Override
    public Iterable<Sensor> getAll() {
        Iterable<Sensor> sensors = super.getAll();

        for (Sensor sensor : sensors) {
            sensor.SensorStatus = this.sensorStatusRepository.get(sensor.Id);
        }

        return sensors;
    }
}

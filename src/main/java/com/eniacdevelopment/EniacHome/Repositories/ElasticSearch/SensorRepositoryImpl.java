package com.eniacdevelopment.EniacHome.Repositories.ElasticSearch;

import com.eniacdevelopment.EniacHome.DataModel.Sensor.Sensor;
import com.eniacdevelopment.EniacHome.Repositories.Shared.SensorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.transport.TransportClient;

import javax.inject.Inject;

/**
 * Created by larsg on 1/6/2017.
 */
public class SensorRepositoryImpl extends RepositoryImpl<Sensor> implements SensorRepository {
    @Inject
    public SensorRepositoryImpl(TransportClient transportClient, ObjectMapper objectMapper) {
        super(transportClient, objectMapper, "sensor", Sensor.class);
    }
}

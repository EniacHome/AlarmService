package com.eniacdevelopment.EniacHome.Repositories.ElasticSearch;

import com.eniacdevelopment.EniacHome.DataModel.Sensor.Sensor;
import com.eniacdevelopment.EniacHome.DataModel.Sensor.SensorType;
import com.eniacdevelopment.EniacHome.Repositories.Shared.SensorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import javax.inject.Inject;

/**
 * Created by larsg on 1/6/2017.
 */
public class SensorRepositoryImpl extends RepositoryImpl<Sensor> implements SensorRepository {
    @Inject
    public SensorRepositoryImpl(TransportClient transportClient, ObjectMapper objectMapper) {
        super(transportClient, objectMapper, "sensor", Sensor.class);
    }

    @Override
    public Iterable<Sensor> getSensors(SensorType sensorType) {
        QueryBuilder query = QueryBuilders.matchQuery("SensorType", sensorType);

        return this.search(query);
    }
}

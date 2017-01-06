package com.eniacdevelopment.EniacHome.Repositories.ElasticSearch;

import com.eniacdevelopment.EniacHome.DataModel.Sensor.SensorInfo;
import com.eniacdevelopment.EniacHome.Repositories.Shared.SensorInfoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.transport.TransportClient;

import javax.inject.Inject;

/**
 * Created by larsg on 1/5/2017.
 */
public class SensorInfoRepositoryImpl extends RepositoryImpl<SensorInfo> implements SensorInfoRepository {
    @Inject
    public SensorInfoRepositoryImpl(TransportClient transportClient, ObjectMapper objectMapper) {
        super(transportClient, objectMapper, "sensorinfo", SensorInfo.class);
    }
}

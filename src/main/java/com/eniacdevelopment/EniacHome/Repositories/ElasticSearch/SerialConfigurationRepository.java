package com.eniacdevelopment.EniacHome.Repositories.ElasticSearch;

import com.eniacdevelopment.EniacHome.DataModel.Configuration.SerialConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.transport.TransportClient;

import javax.inject.Inject;

/**
 * Created by larsg on 12/9/2016.
 */
public class SerialConfigurationRepository extends ConfigurationRepositoryImpl<SerialConfiguration> {

    @Inject
    public SerialConfigurationRepository(TransportClient transportClient, ObjectMapper objectMapper) {
        super(transportClient, objectMapper, SerialConfiguration.class);
    }
}

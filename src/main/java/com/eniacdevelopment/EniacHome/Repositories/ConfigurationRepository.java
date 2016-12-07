package com.eniacdevelopment.EniacHome.Repositories;

import com.eniacdevelopment.EniacHome.DataModel.Configuration.Configuration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by larsg on 11/17/2016.
 */
public class ConfigurationRepository{

    private final TransportClient transportClient;
    private final ObjectMapper objectMapper;

    @Inject
    public ConfigurationRepository(TransportClient transportClient, ObjectMapper objectMapper) {
        this.transportClient = transportClient;
        this.objectMapper = objectMapper;
    }

    public <TConfiguration extends Configuration> List<TConfiguration> getConfigurations(Class<TConfiguration> type) {
        SearchResponse searchResponse = null;
        try {
            searchResponse = this.transportClient.prepareSearch()
                    .setIndices("configuration")
                    .setTypes(type.getName())
                    .execute()
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        List<TConfiguration> configurations = new ArrayList<>();
        for(SearchHit searchHit : searchResponse.getHits()){
            TConfiguration configuration = null;
            try {
                configuration = this.objectMapper.readValue(searchHit.getSourceRef().toBytes(), type);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(configuration != null) {
                configurations.add(configuration);
            }
        }
        return configurations;
    }

    public <TConfiguration extends Configuration> TConfiguration getConfiguration(Class<TConfiguration> type, String id) {
        GetResponse getResponse = null;
        try {
            getResponse = this.transportClient.prepareGet()
                    .setIndex("configuration")
                    .setType(type.getName())
                    .setId(id)
                    .execute()
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        byte[] jsonConfiguration = getResponse.getSourceAsBytes();
        TConfiguration configuration = null;
        try {
            configuration = this.objectMapper.readValue(jsonConfiguration, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configuration;
    }

    public <TConfiguration extends Configuration> TConfiguration getActiveConfiguration(Class<TConfiguration> type){
        SearchResponse searchResponse = null;
        try {
            searchResponse = this.transportClient.prepareSearch()
                    .setIndices("configuration")
                    .setTypes(type.getName())
                    .setQuery(QueryBuilders.termQuery("Active", true))
                    .execute()
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        String id = searchResponse.getHits().getAt(0).id();
        if(Strings.isNullOrEmpty(id))
        {
            return null;
        }

        return getConfiguration(type, id);
    }

    public <TConfiguration extends Configuration> void setConfiguration(TConfiguration configuration){
        byte[] jsonConfiguration;
        try {
            jsonConfiguration = this.objectMapper.writeValueAsBytes(configuration);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        IndexResponse indexResponse = null;
        try {
            indexResponse = this.transportClient.prepareIndex()
                    .setIndex("configuration")
                    .setType(configuration.getClass().getName())
                    .setId(configuration.Id)
                    .setSource(jsonConfiguration)
                    .setRefresh(true)
                    .execute()
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public <TConfiguration extends Configuration> void deleteConfiguration(Class<TConfiguration> type, String id){
        DeleteResponse deleteResponse = null;
        try {
            deleteResponse = this.transportClient.prepareDelete()
                    .setIndex("configuration")
                    .setType(type.getName())
                    .setId(id)
                    .execute()
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

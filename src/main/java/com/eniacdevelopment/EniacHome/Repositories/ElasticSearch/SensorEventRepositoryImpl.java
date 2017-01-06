package com.eniacdevelopment.EniacHome.Repositories.ElasticSearch;

import com.eniacdevelopment.EniacHome.DataModel.Sensor.SensorEvent;
import com.eniacdevelopment.EniacHome.Repositories.Shared.SensorEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by larsg on 1/5/2017.
 */
public class SensorEventRepositoryImpl implements SensorEventRepository {

    private final String index = "sensorevent";
    private final Class<SensorEvent> type = SensorEvent.class;
    private final TransportClient transportClient;
    private final ObjectMapper objectMapper;

    @Inject
    public SensorEventRepositoryImpl(TransportClient transportClient, ObjectMapper objectMapper) {
        this.transportClient = transportClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public void add(SensorEvent sensorEvent) {
        byte[] jsonItem;
        try {
            jsonItem = this.objectMapper.writeValueAsBytes(sensorEvent);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        IndexResponse indexResponse = null;
        try {
            indexResponse = this.transportClient.prepareIndex()
                    .setIndex(this.index)
                    .setType(this.type.getName())
                    .setSource(jsonItem)
                    .setRefresh(true)
                    .execute()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iterable<SensorEvent> get(String id) {
        QueryBuilder query = QueryBuilders.matchQuery("Id", id);

        SearchResponse searchResponse = null;
        try {
            searchResponse = this.transportClient.prepareSearch()
                    .setIndices(this.index)
                    .setTypes(this.type.getName())
                    .setQuery(query)
                    .execute()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }

        List<SensorEvent> items = new ArrayList<>();
        for (SearchHit searchHit : searchResponse.getHits()) {
            SensorEvent item = null;
            try {
                item = this.objectMapper.readValue(searchHit.getSourceRef().toBytes(), type);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (item != null) {
                items.add(item);
            }
        }
        return items;
    }
}

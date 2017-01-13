package com.eniacdevelopment.EniacHome.Repositories.ElasticSearch;

import com.eniacdevelopment.EniacHome.DataModel.Alarm.AlarmEvent;
import com.eniacdevelopment.EniacHome.Repositories.Shared.AlarmEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by larsg on 1/6/2017.
 */
public class AlarmEventRepositoryImpl implements AlarmEventRepository {

    private final String index = "alarmevent";
    private final Class<AlarmEvent> type = AlarmEvent.class;
    private final TransportClient transportClient;
    private final ObjectMapper objectMapper;

    @Inject
    public AlarmEventRepositoryImpl(TransportClient transportClient, ObjectMapper objectMapper) {
        this.transportClient = transportClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public void add(AlarmEvent event) {
        byte[] jsonItem;
        try {
            jsonItem = this.objectMapper.writeValueAsBytes(event);
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
    public Iterable<AlarmEvent> get() {
        SearchResponse searchResponse = null;
        try {
            searchResponse = this.transportClient.prepareSearch()
                    .setIndices(this.index)
                    .setTypes(this.type.getName())
                    .execute()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }

        List<AlarmEvent> items = new ArrayList<>();
        for (SearchHit searchHit : searchResponse.getHits()) {
            AlarmEvent item;
            try {
                item = this.objectMapper.readValue(searchHit.getSourceRef().toBytes(), type);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            if (item != null) {
                items.add(item);
            }
        }
        return items;
    }
}

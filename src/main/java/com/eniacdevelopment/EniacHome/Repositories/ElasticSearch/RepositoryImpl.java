package com.eniacdevelopment.EniacHome.Repositories.ElasticSearch;

import com.eniacdevelopment.EniacHome.DataModel.Entity;
import com.eniacdevelopment.EniacHome.Repositories.Shared.Repository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by larsg on 12/9/2016.
 */
public abstract class RepositoryImpl<T extends Entity> implements Repository<T> {

    protected final TransportClient transportClient;
    protected final ObjectMapper objectMapper;
    protected final String index;
    protected final Class<T> type;

    public RepositoryImpl(TransportClient transportClient, ObjectMapper objectMapper, String index, Class<T> type){
        this.transportClient = transportClient;
        this.objectMapper = objectMapper;
        this.index = index;
        this.type = type;
    }

    @Override
    public void add(T item) {
        byte[] jsonItem;
        try {
            jsonItem = this.objectMapper.writeValueAsBytes(item);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        IndexResponse indexResponse = null;
        try {
            indexResponse = this.transportClient.prepareIndex()
                    .setIndex(this.index)
                    .setType(this.type.getName())
                    .setId(item.Id)
                    .setSource(jsonItem)
                    .setRefresh(true)
                    .execute()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Iterable<T> items) {
        BulkRequestBuilder bulkRequestBuilder = transportClient.prepareBulk();

        for(T item : items)
        {
            byte[] jsonItem;
            try {
                jsonItem = this.objectMapper.writeValueAsBytes(item);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return;
            }

            bulkRequestBuilder.add(
                 this.transportClient.prepareIndex()
                        .setIndex(this.index)
                        .setType(this.type.getName())
                        .setId(item.Id)
                        .setSource(jsonItem)
            );
        }

        BulkResponse bulkResponse = bulkRequestBuilder.execute().actionGet();
    }

    @Override
    public void update(T item) {
        byte[] jsonItem;
        try {
            jsonItem = this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsBytes(item);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        UpdateResponse updateResponse = null;
        try {
            updateResponse = this.transportClient.prepareUpdate()
                    .setIndex(this.index)
                    .setType(this.type.getName())
                    .setId(item.Id)
                    .setDoc(jsonItem)
                    .setRefresh(true)
                    .execute()
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        DeleteResponse deleteResponse = null;
        try {
            deleteResponse = this.transportClient.prepareDelete()
                    .setIndex(this.index)
                    .setType(this.type.getName())
                    .setId(id)
                    .execute()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T get(String id) {
        GetResponse getResponse = null;
        try {
            getResponse = this.transportClient.prepareGet()
                    .setIndex(this.index)
                    .setType(this.type.getName())
                    .setId(id)
                    .execute()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        byte[] jsonItem = getResponse.getSourceAsBytes();
        T item = null;
        try {
            item = this.objectMapper.readValue(jsonItem, this.type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public Iterable<T> getAll() {
        SearchResponse searchResponse = null;
        try {
            searchResponse = this.transportClient.prepareSearch()
                    .setIndices(this.index)
                    .setTypes(this.type.getName())
                    .execute()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        List<T> items = new ArrayList<>();
        for(SearchHit searchHit : searchResponse.getHits()){
            T item = null;
            try {
                item = this.objectMapper.readValue(searchHit.getSourceRef().toBytes(), type);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(item != null) {
                items.add(item);
            }
        }
        return items;
    }

    public List<T> search(QueryBuilder query){
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
        }

        List<T> items = new ArrayList<>();
        for(SearchHit searchHit : searchResponse.getHits()){
            T item = null;
            try {
                item = this.objectMapper.readValue(searchHit.getSourceRef().toBytes(), type);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(item != null) {
                items.add(item);
            }
        }
        return items;
    }
}

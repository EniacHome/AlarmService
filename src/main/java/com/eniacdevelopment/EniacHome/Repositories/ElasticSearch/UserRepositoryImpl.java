package com.eniacdevelopment.EniacHome.Repositories.ElasticSearch;

import com.eniacdevelopment.EniacHome.DataModel.User.User;
import com.eniacdevelopment.EniacHome.Repositories.Shared.UserRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.Utils.UserUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
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
 * Created by larsg on 12/9/2016.
 */
public class UserRepositoryImpl implements UserRepository {
    private final UserUtils userUtils;

    private final TransportClient transportClient;
    private final ObjectMapper objectMapper;
    private final String index = "user";
    private final Class<User> type = User.class;

    @Inject
    public UserRepositoryImpl(TransportClient transportClient, ObjectMapper objectMapper, UserUtils userUtils) {
        this.transportClient = transportClient;
        this.objectMapper = objectMapper;
        this.userUtils = userUtils;
    }

    public User getByUserName(String username) {
        QueryBuilder query = QueryBuilders.matchQuery("Username", username);

        List<User> users = this.search(query);
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public void add(User item) {
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
                    .setSource(jsonItem)
                    .setRefresh(true)
                    .execute()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void add(Iterable<User> items) {
        BulkRequestBuilder bulkRequestBuilder = transportClient.prepareBulk();

        for (User item : items) {
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
                            .setSource(jsonItem)
            );
        }

        BulkResponse bulkResponse = bulkRequestBuilder.execute().actionGet();
    }

    public User get(String id) {
        GetResponse getResponse;
        try {
            getResponse = this.transportClient.prepareGet()
                    .setIndex(this.index)
                    .setType(this.type.getName())
                    .setId(id)
                    .execute()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }

        byte[] jsonItem = getResponse.getSourceAsBytes();
        if (jsonItem == null) {
            return null;
        }

        User item;
        try {
            item = this.objectMapper.readValue(jsonItem, this.type);
            item.Id = getResponse.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return item;
    }

    public Iterable<User> getAll() {
        SearchResponse searchResponse = null;
        try {
            searchResponse = this.transportClient.prepareSearch()
                    .setIndices(this.index)
                    .setTypes(this.type.getName())
                    .execute()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        List<User> items = new ArrayList<>();
        for (SearchHit searchHit : searchResponse.getHits()) {
            User item = null;
            try {
                item = this.objectMapper.readValue(searchHit.getSourceRef().toBytes(), type);
                item.Id = searchHit.getId();
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

    public void update(User item) {
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

    public List<User> search(QueryBuilder query) {
        SearchResponse searchResponse;
        try {
            searchResponse = this.transportClient.prepareSearch()
                    .setIndices(this.index)
                    .setTypes(this.type.getName())
                    .setQuery(query)
                    .execute()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        List<User> items = new ArrayList<>();
        for (SearchHit searchHit : searchResponse.getHits()) {
            User item = null;
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

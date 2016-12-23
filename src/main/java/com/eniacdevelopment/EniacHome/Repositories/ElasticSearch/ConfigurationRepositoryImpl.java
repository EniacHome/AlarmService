package com.eniacdevelopment.EniacHome.Repositories.ElasticSearch;

import com.eniacdevelopment.EniacHome.DataModel.Configuration.Configuration;
import com.eniacdevelopment.EniacHome.Repositories.Shared.ConfigurationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.index.query.QueryBuilders;

import javax.inject.Inject;
import java.util.concurrent.ExecutionException;

/**
 * Created by larsg on 11/17/2016.
 */
public class ConfigurationRepositoryImpl<TConfiguration extends Configuration> extends RepositoryImpl<TConfiguration> implements ConfigurationRepository<TConfiguration>{

    @Inject
    public ConfigurationRepositoryImpl(TransportClient transportClient, ObjectMapper objectMapper, Class<TConfiguration> type) {
        super(transportClient, objectMapper, "configuration", type);
    }

    public TConfiguration getActiveConfiguration(){
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

        return get(id);
    }
}

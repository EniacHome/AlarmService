package com.eniacdevelopment.EniacHome.Repositories.ElasticSearch;

import com.eniacdevelopment.EniacHome.DataModel.User.Token;
import com.eniacdevelopment.EniacHome.Repositories.Shared.TokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by larsg on 12/10/2016.
 */
public class TokenRepositoryImpl extends RepositoryImpl<Token> implements TokenRepository{
    @Inject
    public TokenRepositoryImpl(TransportClient transportClient, ObjectMapper objectMapper) {
        super(transportClient, objectMapper, "token", Token.class);
    }

    @Override
    public Token getByToken(String token) {
        QueryBuilder query = QueryBuilders.matchQuery("Token", token);

        List<Token> tokens = this.search(query);
        return tokens.get(0);
    }
}

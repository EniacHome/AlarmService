package com.eniacdevelopment.EniacHome.Repositories.ElasticSearch;

import com.eniacdevelopment.EniacHome.DataModel.User.Token;
import com.eniacdevelopment.EniacHome.Repositories.Shared.TokenRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.Utils.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.transport.TransportClient;

import javax.inject.Inject;

/**
 * Created by larsg on 12/10/2016.
 */
public class TokenRepositoryImpl extends RepositoryImpl<Token> implements TokenRepository{
    private final TokenUtils tokenUtils;

    @Inject
    public TokenRepositoryImpl(TransportClient transportClient, ObjectMapper objectMapper, TokenUtils tokenUtils) {
        super(transportClient, objectMapper, "token", Token.class);
        this.tokenUtils = tokenUtils;
    }

    @Override
    public String issueToken(String userId) {
        Token token = this.tokenUtils.issueToken(userId);
        this.add(token); /*If already exists; overwrite. ES specific!*/
        return token.Token;
    }

    @Override
    public Boolean AuthenticateToken(Token token) {
        Token dbToken = get(token.Id);
        return this.tokenUtils.AuthenticateToken(token, dbToken);
    }
}

package com.eniacdevelopment.EniacHome.Repositories.ElasticSearch;

import com.eniacdevelopment.EniacHome.DataModel.User.Credentials;
import com.eniacdevelopment.EniacHome.DataModel.User.User;
import com.eniacdevelopment.EniacHome.Repositories.Shared.Objects.UserAuthenticationResult;
import com.eniacdevelopment.EniacHome.Repositories.Shared.UserRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.Utils.UserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by larsg on 12/9/2016.
 */
public class UserRepositoryImpl extends RepositoryImpl<User> implements UserRepository {
    private final UserUtils userUtils;

    @Inject
    public UserRepositoryImpl(TransportClient transportClient, ObjectMapper objectMapper, UserUtils userUtils) {
        super(transportClient, objectMapper, "user", User.class);
        this.userUtils = userUtils;
    }

    @Override
    public UserAuthenticationResult AuthenticateUser(Credentials credentials) {
        QueryBuilder query = QueryBuilders.matchQuery("Username",credentials.Username);

        List<User> users = this.search(query);
        if(users.size() < 1) {
            return new UserAuthenticationResult(){{
                Authenticated = false;
                UserId = null;
            }};
        }
        final User user = users.get(0);

        final Boolean authenticated = this.userUtils.AuthenticateUser(credentials, user);
        return new UserAuthenticationResult(){{
            Authenticated = authenticated;
            UserId = user.Id;
        }};
    }
}

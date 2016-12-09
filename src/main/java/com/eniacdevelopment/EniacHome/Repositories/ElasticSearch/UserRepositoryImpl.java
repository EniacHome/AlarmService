package com.eniacdevelopment.EniacHome.Repositories.ElasticSearch;

import com.eniacdevelopment.EniacHome.DataModel.User.Credentials;
import com.eniacdevelopment.EniacHome.DataModel.User.User;
import com.eniacdevelopment.EniacHome.Repositories.Shared.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.transport.TransportClient;

import javax.inject.Inject;

/**
 * Created by larsg on 12/9/2016.
 */
public class UserRepositoryImpl extends RepositoryImpl<User> implements UserRepository {
    @Inject
    public UserRepositoryImpl(TransportClient transportClient, ObjectMapper objectMapper) {
        super(transportClient, objectMapper, "user", User.class);
    }

    @Override
    public Boolean AuthenticateUser(Credentials credentials) {


        return true;
    }
}

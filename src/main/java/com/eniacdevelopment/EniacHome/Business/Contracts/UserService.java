package com.eniacdevelopment.EniacHome.Business.Contracts;

import com.eniacdevelopment.EniacHome.DataModel.User.Credentials;
import com.eniacdevelopment.EniacHome.DataModel.User.User;
import com.eniacdevelopment.EniacHome.Repositories.Shared.Objects.TokenAuthenticationResult;
import com.eniacdevelopment.EniacHome.Repositories.Shared.Objects.UserAuthenticationResult;

/**
 * Created by larsg on 1/15/2017.
 */
public interface UserService {
    Iterable<User> getUsers();

    User getUser(String id);

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(String id);

    UserAuthenticationResult authenticateUser(Credentials credentials);

    String issueToken(String userId);

    TokenAuthenticationResult authenticateToken(String token);

    void updateToken(String userId);
}

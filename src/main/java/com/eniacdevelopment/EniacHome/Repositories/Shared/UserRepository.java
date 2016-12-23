package com.eniacdevelopment.EniacHome.Repositories.Shared;

import com.eniacdevelopment.EniacHome.DataModel.User.Credentials;
import com.eniacdevelopment.EniacHome.DataModel.User.User;
import com.eniacdevelopment.EniacHome.Repositories.Shared.Objects.UserAuthenticationResult;

/**
 * Created by larsg on 12/9/2016.
 */
public interface UserRepository extends Repository<User> {

    UserAuthenticationResult AuthenticateUser(Credentials credentials);
}

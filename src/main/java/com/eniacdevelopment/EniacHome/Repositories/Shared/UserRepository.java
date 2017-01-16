package com.eniacdevelopment.EniacHome.Repositories.Shared;

import com.eniacdevelopment.EniacHome.DataModel.User.User;

/**
 * Created by larsg on 12/9/2016.
 */
public interface UserRepository extends Repository<User> {
    User getByUserName(String username);
}

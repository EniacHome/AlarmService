package com.eniacdevelopment.EniacHome.Repositories.Shared.Utils;

import com.eniacdevelopment.EniacHome.DataModel.User.Credentials;
import com.eniacdevelopment.EniacHome.DataModel.User.User;

import java.util.Objects;

/**
 * Created by larsg on 12/16/2016.
 */
public class UserUtils {
    public Boolean AuthenticateUser(Credentials credentials, User dbUser){
        if(dbUser == null){
            return false; // If no user supplied from db
        }
        if(!Objects.equals(credentials.Username, dbUser.Username)){
            return false; // If usernames do not match
        }
        if(!Objects.equals(credentials.Password, dbUser.PasswordHash)){
            return false; // If passwords do not match
        }

        return true;
    }
}

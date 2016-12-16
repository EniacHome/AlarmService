package com.eniacdevelopment.EniacHome.Repositories.Shared;

import com.eniacdevelopment.EniacHome.DataModel.User.Token;

/**
 * Created by larsg on 12/10/2016.
 */
public interface TokenRepository extends Repository<Token> {
    String issueToken(String userId);
    Boolean AuthenticateToken(String token);
}

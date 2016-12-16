package com.eniacdevelopment.EniacHome.Repositories.Shared;

import com.eniacdevelopment.EniacHome.DataModel.User.Token;
import com.eniacdevelopment.EniacHome.Repositories.Shared.Objects.TokenAuthenticationResult;

/**
 * Created by larsg on 12/10/2016.
 */
public interface TokenRepository extends Repository<Token> {
    String issueToken(String userId);
    TokenAuthenticationResult authenticateToken(String token);
}

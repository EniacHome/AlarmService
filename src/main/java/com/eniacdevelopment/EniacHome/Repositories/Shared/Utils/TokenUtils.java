package com.eniacdevelopment.EniacHome.Repositories.Shared.Utils;

import com.eniacdevelopment.EniacHome.DataModel.User.Token;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by larsg on 12/16/2016.
 */
public class TokenUtils {
    private final long ONE_MINUTE_IN_MILLISECONDS = 60000;
    private final long MINUTES = 10;

    public Token issueToken(final String userId){
        // Get Token
        Random random = new SecureRandom();
        final String tokenString = new BigInteger(130, random).toString(32);

        // Get Expiry Date
        Calendar currentDate = Calendar.getInstance();
        long curentMilliSeconds = currentDate.getTimeInMillis();
        final Date expiryDate = new Date(curentMilliSeconds + (MINUTES * ONE_MINUTE_IN_MILLISECONDS));

        return new Token(){{
            Id = userId;
            ExpiryDate = expiryDate;
            Token = tokenString;
        }} ;
    }

    public Boolean AuthenticateToken(Token token, Token dbToken) {
        if(dbToken == null) {
            return false; // If no token supplied from db
        }
        if(token.Token != dbToken.Token){
            return false; // If the token strings do not match
        }
        if(dbToken.ExpiryDate.before(new Date())){
            return false; // If token is no longer valid
        }

        return true;
    }
}

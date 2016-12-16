package com.eniacdevelopment.EniacHome.Resources;

import com.eniacdevelopment.EniacHome.DataModel.User.Credentials;
import com.eniacdevelopment.EniacHome.DataModel.User.Token;
import com.eniacdevelopment.EniacHome.Repositories.Shared.TokenRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.UserRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by larsg on 12/9/2016.
 */
@Path("/authentication")
public class AuthenticationResource {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Inject
    public AuthenticationResource(UserRepository userRepository, TokenRepository tokenRepository){
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticate(Credentials credentials){
        if (!this.userRepository.AuthenticateUser(credentials)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Token token = this.tokenRepository.issueToken(credentials.Username);

        return Response.ok(token).build();
    }
}

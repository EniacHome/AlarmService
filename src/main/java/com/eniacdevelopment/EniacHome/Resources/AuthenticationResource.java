package com.eniacdevelopment.EniacHome.Resources;

import com.eniacdevelopment.EniacHome.DataModel.User.Credentials;
import com.eniacdevelopment.EniacHome.Repositories.Shared.Objects.UserAuthenticationResult;
import com.eniacdevelopment.EniacHome.Repositories.Shared.TokenRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.UserRepository;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by larsg on 12/9/2016.
 */
@PermitAll
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
        UserAuthenticationResult result = this.userRepository.AuthenticateUser(credentials);
        if (!result.Authenticated) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String token = this.tokenRepository.issueToken(result.UserId);

        return Response.ok(token).build();
    }
}

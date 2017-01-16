package com.eniacdevelopment.EniacHome.Resources;

import com.eniacdevelopment.EniacHome.Business.Contracts.UserService;
import com.eniacdevelopment.EniacHome.DataModel.User.Credentials;
import com.eniacdevelopment.EniacHome.Repositories.Shared.Objects.UserAuthenticationResult;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by larsg on 12/9/2016.
 */
@PermitAll
@Path("/authentication")
public class AuthenticationResource {

    private final UserService userService;

    @Inject
    public AuthenticationResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticate(Credentials credentials){
        UserAuthenticationResult result = this.userService.authenticateUser(credentials);
        if (!result.Authenticated) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String token = this.userService.issueToken(result.UserId);

        return Response.ok(token).build();
    }
}

package com.eniacdevelopment.EniacHome.Resources;

import com.eniacdevelopment.EniacHome.DataModel.User.Credentials;
import com.eniacdevelopment.EniacHome.DataModel.User.User;
import com.eniacdevelopment.EniacHome.Repositories.Shared.UserRepository;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by larsg on 12/9/2016.
 */
@Path("/authentication")
public class AuthenticationResource {

    private final UserRepository userRepository;

    @Inject
    public AuthenticationResource(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GET
    public void hitDelete(){
        User user = new User(){{
            Id = "123";
            Username = "xxyy";
            Firstname = "Denise";
            Lastname = "Smith";
        }};

        this.userRepository.delete(user.Id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticate(Credentials credentials){
        return Response.ok().build();
    }
}

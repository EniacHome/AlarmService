package com.eniacdevelopment.EniacHome.Resources;

import com.eniacdevelopment.EniacHome.Business.Contracts.UserService;
import com.eniacdevelopment.EniacHome.DataModel.User.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by larsg on 12/9/2016.
 */
@Path("user")
public class UserResource {

    private final UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Iterable<User> getUsers(){
        return this.userService.getUsers();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") String id){
        return this.userService.getUser(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addUser(User user){
        this.userService.addUser(user);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateUser(User user){
        this.userService.updateUser(user);
    }

    @DELETE
    @Path("{id}")
    public void deleteUser(@PathParam("id") String id){
        this.userService.deleteUser(id);
    }
}

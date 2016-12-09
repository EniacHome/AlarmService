package com.eniacdevelopment.EniacHome.Resources;

import com.eniacdevelopment.EniacHome.DataModel.User.User;
import com.eniacdevelopment.EniacHome.Repositories.Shared.UserRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by larsg on 12/9/2016.
 */
@Path("user")
public class UserResource {

    private final UserRepository userRepository;

    public UserResource(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GET
    public Iterable<User> getUsers(){
        return this.userRepository.getAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") String id){
        return this.userRepository.get(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addUser(User user){
        this.userRepository.add(user);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateUser(User user){
        this.userRepository.update(user);
    }

    @DELETE
    @Path("{id}")
    public void deleteUser(@PathParam("id") String id){
        this.userRepository.delete(id);
    }
}

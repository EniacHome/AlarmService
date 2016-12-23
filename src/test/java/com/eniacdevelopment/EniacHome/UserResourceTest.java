package com.eniacdevelopment.EniacHome;

import com.eniacdevelopment.EniacHome.DataModel.User.User;
import com.eniacdevelopment.EniacHome.DataModel.User.UserRole;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.management.relation.Role;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by larsg on 12/9/2016.
 */
public class UserResourceTest {
    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        server = UnitTestShared.getServer();
        target = UnitTestShared.getWebTarger();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void hitIT(){
        Response rs = target.path("authentication").request().get();

        int x = 5;
    }

    @Test
    public void updateUser(){

        final List<UserRole> roleSet = new ArrayList<UserRole>(){{
            add(UserRole.Admin);
            add(UserRole.User);
        }};

        User user = new User(){{
            Id = "lg";
            Username = "Lars";
            Firstname = "Lars";
            Lastname = "Gardien";
            PasswordHash = "Password";
            Roles = roleSet;
        }};

        this.target.path("user").request().put(Entity.json(user));
    }

    @Test
    public void addUser(){

        final List<UserRole> roleSet = new ArrayList<UserRole>(){{
            add(UserRole.Admin);
            add(UserRole.User);
        }};

        User user = new User(){{
            Id = "lg1";
            Username = "Lars";
            Firstname = "Lars";
            Lastname = "Gardien";
            PasswordHash = "Password";
            Roles = roleSet;
        }};

        this.target.path("user").request().post(Entity.json(user));
    }

    @Test
    public void deleteUser(){
        this.target.path("user").path("lg1").request().delete();
    }

}

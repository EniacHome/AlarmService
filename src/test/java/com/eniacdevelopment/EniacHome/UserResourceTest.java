package com.eniacdevelopment.EniacHome;

import com.eniacdevelopment.EniacHome.DataModel.User.Credentials;
import com.eniacdevelopment.EniacHome.DataModel.User.User;
import com.eniacdevelopment.EniacHome.DataModel.User.UserRole;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void x() {
        Credentials credentials = new Credentials() {{
            Username = "ft";
            Password = "ft";
        }};

        Response response = target.path("authentication").request().post(Entity.json(credentials));
    }

    @Test
    public void getUsers(){
        Iterable<User> response = target.path("user").request().get(new GenericType<Iterable<User>>(){});
        assertTrue(response != null);
    }

    @Test
    public void addUser(){
        User user = new User(){{
            Username = "Mlezi";
            Firstname = "Lars";
            Lastname = "Gardien";
            PasswordHash = "Password";
            Role = UserRole.Admin;
        }};

        this.target.path("user").request().post(Entity.json(user));
    }

    @Test
    public void getUser(){
        User response = target.path("user").path("AVmy_EvV1E_NzL_cpS59").request().get(User.class);
        assertEquals("lg", response.Id);
    }

    @Test
    public void updateUser(){
        User user = new User(){{
            Id = "AVmzFsVu1E_NzL_cpS6D";
            Username = "LarsjeParsje";
        }};

        this.target.path("user").request().put(Entity.json(user));
    }

    @Test
    public void deleteUser(){
        this.target.path("user").path("AVmzFsVu1E_NzL_cpS6D").request().delete();
    }

}

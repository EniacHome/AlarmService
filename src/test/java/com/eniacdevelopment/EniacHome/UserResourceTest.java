package com.eniacdevelopment.EniacHome;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

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
}

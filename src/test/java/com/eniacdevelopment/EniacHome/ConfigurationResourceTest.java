package com.eniacdevelopment.EniacHome;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import com.eniacdevelopment.EniacHome.Application.Main;
import com.eniacdevelopment.EniacHome.DataModel.Configuration.SerialConfiguration;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConfigurationResourceTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient()
                .register(JacksonJsonProvider.class);

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    //NOTE these test are bullcrap, they test elastic!
    @Test
    public void setIt(){
        SerialConfiguration config = new SerialConfiguration(){{
            Id = "TESTCONFIG";
            Active = true;
            BaudRate = 10417;
            DataBits = 8;
            Parity = 0;
            StopBits = 1;
            PortDescriptor = "Something";
        }};

        Response response = target.path("configuration").path("serial").request().post(Entity.json(config));
    }

    @Test
    public void getIt() {
        SerialConfiguration response = target.path("configuration").path("serial").path("TESTCONFIG").request().get(SerialConfiguration.class);
        assertEquals("Something", response.PortDescriptor);
    }

    @Test
    public void getActive(){
        SerialConfiguration response = target.path("configuration").path("serial").path("active").request().get(SerialConfiguration.class);
        assertEquals(true, response.Active);
    }

    @Test
    public void getAll() {
        List<SerialConfiguration> response = target.path("configuration").path("serial").request().get(new GenericType<List<SerialConfiguration>>(){});
        assertTrue(response.size() > 0);
    }

    @Test
    public void deleteIt(){
        Response response = target.path("configuration").path("serial").path("TESTCONFIG").request().delete();
    }
}

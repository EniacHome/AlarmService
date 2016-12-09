package com.eniacdevelopment.EniacHome;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import com.eniacdevelopment.EniacHome.DataModel.Configuration.SerialConfiguration;
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
        server = UnitTestShared.getServer();
        target = UnitTestShared.getWebTarger();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    //NOTE these test are bullcrap, they test elastic!
    @Test
    public void setIt(){
        SerialConfiguration config = new SerialConfiguration(){{
            Id = "CONFIG";
            Active = true;
            BaudRate = 10417;
            DataBits = 8;
            Parity = 0;
            StopBits = 1;
            PortDescriptor = "COM3";
        }};

        Response response = target.path("configuration").path("serial").request().post(Entity.json(config));
    }

    @Test
    public void getIt() {
        SerialConfiguration response = target.path("configuration").path("serial").path("CONFIG").request().get(SerialConfiguration.class);
        assertEquals("COM3", response.PortDescriptor);
    }

    @Test
    public void getActive(){
        SerialConfiguration response = target.path("configuration").path("serial").path("active").request().get(SerialConfiguration.class);
        assertEquals(true, response.Active);
    }

    @Test
    public void getAll() {
        Iterable<SerialConfiguration> response = target.path("configuration").path("serial").request().get(new GenericType<Iterable<SerialConfiguration>>(){});
        assertTrue(response != null);
    }

    @Test
    public void deleteIt(){
        Response response = target.path("configuration").path("serial").path("CONFIG").request().delete();
    }
}

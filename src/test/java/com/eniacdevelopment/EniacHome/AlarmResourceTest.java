package com.eniacdevelopment.EniacHome;

import com.eniacdevelopment.EniacHome.DataModel.Alarm.AlarmStatus;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.WebTarget;

/**
 * Created by larsg on 1/7/2017.
 */
public class AlarmResourceTest {
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
    public void getStatus() {
        AlarmStatus response = target.path("alarm").request().get(AlarmStatus.class);
    }

    @Test
    public void enableAlarm() {
        this.target.path("alarm").path("enable").path("5").request().get();
    }

    @Test
    public void disableAlarm() {
        this.target.path("alarm").path("disable").request().get();
    }
}

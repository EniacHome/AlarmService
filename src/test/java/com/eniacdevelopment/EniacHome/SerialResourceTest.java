package com.eniacdevelopment.EniacHome;

import com.eniacdevelopment.EniacHome.Serial.Objects.SensorNotification;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.media.sse.EventListener;
import org.glassfish.jersey.media.sse.EventSource;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.WebTarget;

/**
 * Created by larsg on 11/26/2016.
 */
public class SerialResourceTest {

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
    public void getSerialEvents(){
        EventSource eventSource = EventSource.target(target.path("serial")).build();
        final SensorNotification[] serialNotification = new SensorNotification[1];
        final Boolean[] shouldContinue = {false};

        EventListener listener = new EventListener() {
            @Override
            public void onEvent(InboundEvent inboundEvent) {
                SensorNotification not = inboundEvent.readData(SensorNotification.class);
                System.out.println(inboundEvent.getName() + ": " + not.Id);
                serialNotification[0] = not;
                shouldContinue[0] = true;
            }
        };
        eventSource.register(listener);
        eventSource.open();

        while(!shouldContinue[0]){}

        eventSource.close();

        Assert.assertEquals("72", serialNotification[0].Id);
    }
}

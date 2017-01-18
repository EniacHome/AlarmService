package com.eniacdevelopment.EniacHome;

import com.eniacdevelopment.EniacHome.DataModel.Sensor.Sensor;
import com.eniacdevelopment.EniacHome.DataModel.Sensor.SensorType;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by larsg on 1/6/2017.
 */
public class SensorResourceTest {
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
    public void getSensors() {
        Iterable<Sensor> response = target.path("sensor").request().get(new GenericType<Iterable<Sensor>>() {
        });
        assertTrue(response != null);
    }

    @Test
    public void getSensorsByType() {
        Iterable<Sensor> response = target.path("sensor").path("type").path(SensorType.ContactSensor.name()).request().get(new GenericType<Iterable<Sensor>>() {
        });
        assertTrue(response != null);
    }

    @Test
    public void addSensor() {
        Sensor Sensor = new Sensor() {{
            Id = "3";
            Name = "ContactSensor index 3";
            SensorType = com.eniacdevelopment.EniacHome.DataModel.Sensor.SensorType.ContactSensor;
            Enabled = true;
            Level = 5;

            CompareMethod = com.eniacdevelopment.EniacHome.DataModel.Sensor.CompareMethod.Equal;
            CompareValue = 0;

            SensorStatus = null;
        }};

        this.target.path("sensor").request().post(Entity.json(Sensor));
    }

    @Test
    public void getSensor() {
        Sensor response = target.path("sensor").path("1").request().get(Sensor.class);
        assertEquals("1", response.Id);
    }

    @Test
    public void updateSensor() {
        Sensor Sensor = new Sensor() {{
            Id = "1";
            Enabled = false;
        }};

        this.target.path("sensor").request().put(Entity.json(Sensor));
    }

    @Test
    public void deleteSensor() {
        this.target.path("sensor").path("3").request().delete();
    }
}

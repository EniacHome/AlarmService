package com.eniacdevelopment.EniacHome.Resources;

import com.eniacdevelopment.EniacHome.Business.Contracts.SensorService;
import com.eniacdevelopment.EniacHome.DataModel.Sensor.Sensor;
import com.eniacdevelopment.EniacHome.DataModel.Sensor.SensorType;
import com.eniacdevelopment.EniacHome.Serial.SerialSubject;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by larsg on 12/27/2016.
 */
@Path("sensor")
public class SensorResource {

    private final SensorService sensorService;
    private final SerialSubject serialSubject;

    @Inject
    public SensorResource(SensorService sensorService, SerialSubject serialSubject) {
        this.sensorService = sensorService;
        this.serialSubject = serialSubject;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Iterable<Sensor> getSensors() {
        return this.sensorService.getAllSensors();
    }

    @GET
    @Path("type/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Iterable<Sensor> getSensorBySensorType(@PathParam("type") SensorType sensorType) {
        return this.sensorService.getSensors(sensorType);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Sensor getSensor(@PathParam("id") String id) {
        return this.sensorService.getSensor(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addSensor(Sensor sensor) {
        this.sensorService.addSensor(sensor);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateSensor(Sensor sensor) {
        this.sensorService.updateSensor(sensor);
    }

    @DELETE
    @Path("{id}")
    public void deleteSensor(@PathParam("id") String id) {
        this.sensorService.deleteSensor(id);
    }
}

package com.eniacdevelopment.EniacHome.Resources;

import com.eniacdevelopment.EniacHome.DataModel.Sensor.Sensor;
import com.eniacdevelopment.EniacHome.Repositories.Shared.SensorRepository;
import com.eniacdevelopment.EniacHome.Serial.SerialSubject;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by larsg on 12/27/2016.
 */
@Path("sensor")
public class SensorResource {

    private final SensorRepository sensorRepository;
    private final SerialSubject serialSubject;

    @Inject
    public SensorResource(SensorRepository sensorRepository, SerialSubject serialSubject) {
        this.sensorRepository = sensorRepository;
        this.serialSubject = serialSubject;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Iterable<Sensor> getSensors() {
        return this.sensorRepository.getAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Sensor getSensor(@PathParam("id") String id) {
        return this.sensorRepository.get(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addSensor(Sensor sensor) {
        this.sensorRepository.add(sensor);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateSensor(Sensor sensor) {
        this.sensorRepository.update(sensor);
    }

    @DELETE
    @Path("{id}")
    public void deleteSensor(@PathParam("id") String id) {
        this.sensorRepository.delete(id);
    }
}

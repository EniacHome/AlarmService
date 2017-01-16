package com.eniacdevelopment.EniacHome.Business.Contracts;

import com.eniacdevelopment.EniacHome.DataModel.Sensor.Sensor;

/**
 * Created by larsg on 1/13/2017.
 */
public interface SensorService {
    Iterable<Sensor> getAllSensors();

    Sensor getSensor(String id);

    void addSensor(Sensor sensor);

    void updateSensor(Sensor sensor);

    void deleteSensor(String id);
}

package com.eniacdevelopment.EniacHome.Repositories.Shared;

import com.eniacdevelopment.EniacHome.DataModel.Sensor.Sensor;
import com.eniacdevelopment.EniacHome.DataModel.Sensor.SensorType;

/**
 * Created by larsg on 1/6/2017.
 */
public interface SensorRepository extends Repository<Sensor> {
    Iterable<Sensor> getSensors(SensorType sensorType);
}

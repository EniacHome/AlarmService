package com.eniacdevelopment.EniacHome.Repositories.Shared;

import com.eniacdevelopment.EniacHome.DataModel.Sensor.SensorEvent;

/**
 * Created by larsg on 1/5/2017.
 */
public interface SensorEventRepository {
    void add(SensorEvent sensorEvent);

    Iterable<SensorEvent> get(String Id);
}

package com.eniacdevelopment.EniacHome.Repositories.Shared;

import com.eniacdevelopment.EniacHome.DataModel.Sensor.SensorStatus;

/**
 * Created by larsg on 1/5/2017.
 */
public interface SensorStatusRepository {
    void put(String id, SensorStatus item);

    SensorStatus get(String Id);
}

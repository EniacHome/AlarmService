package com.eniacdevelopment.EniacHome.Repositories.Shared;

import com.eniacdevelopment.EniacHome.DataModel.Sensor.SensorStatus;

import java.util.Map;
import java.util.Set;

/**
 * Created by larsg on 1/5/2017.
 */
public interface SensorStatusRepository {
    void put(String id, SensorStatus item);

    SensorStatus get(String Id);

    Set<Map.Entry<String, SensorStatus>> getAll();
}

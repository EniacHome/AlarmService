package com.eniacdevelopment.EniacHome.Repositories.InMemory;

import com.eniacdevelopment.EniacHome.DataModel.Sensor.SensorStatus;
import com.eniacdevelopment.EniacHome.Repositories.Shared.SensorStatusRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

/**
 * Created by larsg on 1/5/2017.
 */
public class SensorStatusRepositoryImpl implements SensorStatusRepository {

    public static final String SENSOR_STATUS_DICTIONARY_NAME = "Sensor_Status_Dictionary";
    private final Map<String, SensorStatus> memory;

    @Inject
    @Named(SENSOR_STATUS_DICTIONARY_NAME)
    public SensorStatusRepositoryImpl(Map<String, SensorStatus> memory) {
        this.memory = memory;
    }

    @Override
    public void put(String id, SensorStatus item) {
        this.memory.put(id, item);
    }

    @Override
    public SensorStatus get(String Id) {
        return this.memory.get(Id);
    }
}

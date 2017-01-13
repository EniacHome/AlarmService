package com.eniacdevelopment.EniacHome.Business;

import com.eniacdevelopment.EniacHome.Business.Contracts.SensorService;
import com.eniacdevelopment.EniacHome.DataModel.Sensor.Sensor;
import com.eniacdevelopment.EniacHome.Repositories.Shared.SensorRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.SensorStatusRepository;

import javax.inject.Inject;

/**
 * Created by larsg on 1/13/2017.
 */
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;
    private final SensorStatusRepository sensorStatusRepository;

    @Inject
    public SensorServiceImpl(SensorRepository sensorRepository, SensorStatusRepository sensorStatusRepository) {
        this.sensorRepository = sensorRepository;
        this.sensorStatusRepository = sensorStatusRepository;
    }

    @Override
    public Iterable<Sensor> getAllSensors() {
        Iterable<Sensor> sensors = this.sensorRepository.getAll();
        for (Sensor sensor : sensors) {
            sensor.SensorStatus = this.sensorStatusRepository.get(sensor.Id);
        }

        return sensors;
    }

    @Override
    public Sensor getSensor(String id) {
        Sensor sensor = this.sensorRepository.get(id);
        sensor.SensorStatus = this.sensorStatusRepository.get(id);

        return sensor;
    }

    @Override
    public void addSensor(Sensor sensor) {
        this.sensorRepository.add(sensor);
    }

    @Override
    public void updateSensor(Sensor sensor) {
        this.sensorRepository.update(sensor);
    }

    @Override
    public void deleteSensor(String id) {
        this.sensorRepository.delete(id);
    }
}

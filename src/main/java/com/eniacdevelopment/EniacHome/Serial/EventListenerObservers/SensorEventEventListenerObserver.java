
package com.eniacdevelopment.EniacHome.Serial.EventListenerObservers;

import com.eniacdevelopment.EniacHome.DataModel.Sensor.SensorEvent;
import com.eniacdevelopment.EniacHome.DataModel.Sensor.SensorStatus;
import com.eniacdevelopment.EniacHome.Repositories.Shared.SensorEventRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.SensorStatusRepository;

import javax.inject.Inject;

/**
 * Created by larsg on 9/28/2016.
 */
public class SensorEventEventListenerObserver extends EventListenerObserver {

    private final SensorEventRepository sensorEventRepository;
    private final SensorStatusRepository sensorStatusRepository;

    @Inject
    public SensorEventEventListenerObserver(SensorEventRepository sensorEventRepository, SensorStatusRepository sensorStatusRepository) {
        this.sensorEventRepository = sensorEventRepository;
        this.sensorStatusRepository = sensorStatusRepository;
    }

    @Override
    public void eventNotify(String sensorId) {
        SensorStatus sensorStatus = this.sensorStatusRepository.get(sensorId);

        SensorEvent sensorEvent = new SensorEvent() {{
            SensorId = sensorId;
            Value = sensorStatus.Value;
            Date = sensorStatus.Date;
            Alarmed = sensorStatus.Alarmed;
        }};

        this.sensorEventRepository.add(sensorEvent);
    }
}

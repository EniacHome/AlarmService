
package com.eniacdevelopment.EniacHome.Serial.PacketListenerObservers;

import com.eniacdevelopment.EniacHome.DataModel.Sensor.SensorEvent;
import com.eniacdevelopment.EniacHome.Repositories.Shared.SensorEventRepository;
import com.eniacdevelopment.EniacHome.Serial.Objects.SensorNotification;

import javax.inject.Inject;

/**
 * Created by larsg on 9/28/2016.
 */
public class SensorEventPacketListenerObserver extends PacketListenerObserver {

    private final SensorEventRepository sensorEventRepository;

    @Inject
    public SensorEventPacketListenerObserver(SensorEventRepository sensorEventRepository) {
        this.sensorEventRepository = sensorEventRepository;
    }

    @Override
    public void eventNotify(SensorNotification sensorNotification) {
        SensorEvent sensorEvent = new SensorEvent() {{
            Id = sensorNotification.Id;
            Value = sensorNotification.Value;
            Date = sensorNotification.date;
        }};

        this.sensorEventRepository.add(sensorEvent);
    }
}

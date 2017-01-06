package com.eniacdevelopment.EniacHome.Serial.PacketListenerObservers;

import com.eniacdevelopment.EniacHome.DataModel.Sensor.SensorStatus;
import com.eniacdevelopment.EniacHome.Repositories.Shared.SensorStatusRepository;
import com.eniacdevelopment.EniacHome.Serial.Objects.SensorNotification;

import javax.inject.Inject;

/**
 * Created by larsg on 1/5/2017.
 */
public class SensorStatusPacketListenerObserver extends PacketListenerObserver {

    private final SensorStatusRepository sensorStatusRepository;

    @Inject
    public SensorStatusPacketListenerObserver(SensorStatusRepository sensorStatusRepository) {
        this.sensorStatusRepository = sensorStatusRepository;
    }

    @Override
    public void eventNotify(SensorNotification sensorNotification) {
        SensorStatus sensorStatus = new SensorStatus() {{
            Id = sensorNotification.Id;
            Value = sensorNotification.Value;
        }};

        this.sensorStatusRepository.put(sensorStatus);
    }
}

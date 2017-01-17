package com.eniacdevelopment.EniacHome.Serial.EventListenerObservers;

import com.eniacdevelopment.EniacHome.Business.Contracts.SensorService;
import com.eniacdevelopment.EniacHome.DataModel.Sensor.Sensor;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;

import javax.inject.Inject;

/**
 * Created by larsg on 1/17/2017.
 */
public class AndroidListenerObserver extends EventListenerObserver  {

    private final Sender sender;
    private final SensorService sensorService;

    @Inject
    public AndroidListenerObserver(Sender sender, SensorService sensorService){

        this.sender = sender;
        this.sensorService = sensorService;
    }

    @Override
    public void eventNotify(String sensorId) {
        //Get all SensorInfo from the repository
        Sensor sensor = this.sensorService.getSensor(sensorId);

        if (sensor == null) {
            sensor = new Sensor() {{
                Id = sensorId;
                Name = "Unknown";
                Level = Integer.MAX_VALUE;
                SensorType = com.eniacdevelopment.EniacHome.DataModel.Sensor.SensorType.Unknown;
                Enabled = false;
                SensorStatus = null;
            }};
        }

        Message message = new Message.Builder()
                .priority(sensor.SensorStatus.Alarmed ? Message.Priority.HIGH : Message.Priority.NORMAL)
                .build();
    }
}

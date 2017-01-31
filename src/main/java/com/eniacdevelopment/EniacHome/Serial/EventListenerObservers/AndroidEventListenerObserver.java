package com.eniacdevelopment.EniacHome.Serial.EventListenerObservers;

import com.eniacdevelopment.EniacHome.Business.Contracts.SensorService;
import com.eniacdevelopment.EniacHome.DataModel.Sensor.Sensor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by larsg on 1/17/2017.
 */
public class AndroidEventListenerObserver extends EventListenerObserver {

    private final Sender sender;
    private final SensorService sensorService;
    private final ObjectMapper objectMapper;

    @Inject
    public AndroidEventListenerObserver(Sender sender, SensorService sensorService, ObjectMapper objectMapper) {
        this.sender = sender;
        this.sensorService = sensorService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void eventNotify(String sensorId) {
        //Get all SensorInfo from the repository
        Sensor sensor = this.sensorService.getSensor(sensorId);

        Map<String, Object> mapSensor = this.objectMapper.convertValue(sensor, Map.class);

        Message.Builder message = new Message.Builder()
                .priority(sensor.SensorStatus.Alarmed ? Message.Priority.HIGH : Message.Priority.NORMAL);

        for (Map.Entry<String, Object> mapEntry : mapSensor.entrySet()) {
            message.addData(mapEntry.getKey(), mapEntry.getValue().toString());
        }
    }
}

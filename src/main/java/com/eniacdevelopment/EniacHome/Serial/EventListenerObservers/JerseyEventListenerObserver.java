package com.eniacdevelopment.EniacHome.Serial.EventListenerObservers;

import com.eniacdevelopment.EniacHome.Business.Contracts.SensorService;
import com.eniacdevelopment.EniacHome.DataModel.Sensor.Sensor;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

/**
 * Created by larsg on 11/26/2016.
 */
public class JerseyEventListenerObserver extends EventListenerObserver {
    private final SseBroadcaster broadcaster;
    private final SensorService sensorService;

    @Inject
    public JerseyEventListenerObserver(SseBroadcaster broadcaster, SensorService sensorService) {
        this.broadcaster = broadcaster;
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

        //Transmit the sensor
        OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
        OutboundEvent event = eventBuilder.name("SensorNotification")
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .data(Sensor.class, sensor)
                .build();

        broadcaster.broadcast(event);
    }
}

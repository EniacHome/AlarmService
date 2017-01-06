package com.eniacdevelopment.EniacHome.Serial.PacketListenerObservers;

import com.eniacdevelopment.EniacHome.DataModel.Sensor.Sensor;
import com.eniacdevelopment.EniacHome.Repositories.Shared.SensorRepository;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

/**
 * Created by larsg on 11/26/2016.
 */
public class JerseyPacketListenerObserver extends PacketListenerObserver {
    private final SseBroadcaster broadcaster;
    private final SensorRepository sensorInfoRepository;

    @Inject
    public JerseyPacketListenerObserver(SseBroadcaster broadcaster, SensorRepository sensorInfoRepository) {
        this.broadcaster = broadcaster;
        this.sensorInfoRepository = sensorInfoRepository;
    }

    @Override
    public void eventNotify(String sensorId) {
        //Get all SensorInfo from the repository
        Sensor sensor = this.sensorInfoRepository.get(sensorId);

        //Transmit the sensor
        OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
        OutboundEvent event = eventBuilder.name("SensorNotification")
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .data(Sensor.class, sensor)
                .build();

        broadcaster.broadcast(event);
    }
}

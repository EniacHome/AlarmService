package com.eniacdevelopment.EniacHome.Serial.PacketListenerObservers;

import com.eniacdevelopment.EniacHome.DataModel.Sensor.Sensor;
import com.eniacdevelopment.EniacHome.DataModel.Sensor.SensorInfo;
import com.eniacdevelopment.EniacHome.Repositories.Shared.SensorInfoRepository;
import com.eniacdevelopment.EniacHome.Serial.Objects.SensorNotification;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

/**
 * Created by larsg on 11/26/2016.
 */
public class JerseyPacketListenerObserver extends PacketListenerObserver {
    private final SseBroadcaster broadcaster;
    private final SensorInfoRepository sensorInfoRepository;

    @Inject
    public JerseyPacketListenerObserver(SseBroadcaster broadcaster, SensorInfoRepository sensorInfoRepository) {
        this.broadcaster = broadcaster;
        this.sensorInfoRepository = sensorInfoRepository;
    }

    @Override
    public void eventNotify(SensorNotification sensorNotification) {
        SensorInfo sensorInfo = this.sensorInfoRepository.get(sensorNotification.Id);

        Sensor sensor = new Sensor() {{
            Id = sensorNotification.Id;
            Name = sensorInfo == null ? null : sensorInfo.Name;
            SensorType = sensorInfo == null ? null : sensorInfo.SensorType;
            Value = sensorNotification.Value;
            Updated = sensorNotification.date;
        }};

        OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
        OutboundEvent event = eventBuilder.name("SensorNotification")
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .data(Sensor.class, sensor)
                .build();

        broadcaster.broadcast(event);
    }
}

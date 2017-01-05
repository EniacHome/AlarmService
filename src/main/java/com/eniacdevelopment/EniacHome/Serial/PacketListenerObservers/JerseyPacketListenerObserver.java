package com.eniacdevelopment.EniacHome.Serial.PacketListenerObservers;

import com.eniacdevelopment.EniacHome.Serial.Objects.SerialNotification;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

/**
 * Created by larsg on 11/26/2016.
 */
public class JerseyPacketListenerObserver extends PacketListenerObserver {
    private final SseBroadcaster broadcaster;

    @Inject
    public JerseyPacketListenerObserver(SseBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    @Override
    public void eventNotify(SerialNotification serialNotification) {


        OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
        OutboundEvent event = eventBuilder.name("SerialNotification")
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .data(SerialNotification.class, serialNotification)
                .build();

        broadcaster.broadcast(event);
    }
}

package com.eniacdevelopment.EniacHome.Resources;

import com.eniacdevelopment.EniacHome.Serial.PacketListenerObserver;
import com.eniacdevelopment.EniacHome.Serial.SerialNotification;
import com.eniacdevelopment.EniacHome.Serial.SerialSubject;
import org.glassfish.hk2.api.Immediate;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by larsg on 11/26/2016.
 */
@Immediate
@Path("serial")
public class SerialResource {

    private final SerialSubject serialSubject;
    private final SseBroadcaster broadcaster;

    @Inject
    public SerialResource(SerialSubject serialSubject){
        this.serialSubject = serialSubject;
        this.broadcaster = new SseBroadcaster();

        this.serialSubject.addObserver(new PacketListenerObserver() {
            @Override
            public void eventNotify(SerialNotification serialNotification) {
                OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
                OutboundEvent event = eventBuilder.name("SerialNotification")
                        .mediaType(MediaType.APPLICATION_JSON_TYPE)
                        .data(SerialNotification.class, serialNotification)
                        .build();

                broadcaster.broadcast(event);
            }
        });
    }

    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput subscribeSerial(){
        final EventOutput eventOutput = new EventOutput();
        this.broadcaster.add(eventOutput);
        return eventOutput;
    }
}

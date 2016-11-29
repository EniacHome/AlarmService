package com.eniacdevelopment.EniacHome.Resources;

import org.glassfish.hk2.api.Immediate;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by larsg on 11/26/2016.
 */
@Immediate
@Path("serial")
public class SerialResource {

    private final SseBroadcaster broadcaster;

    @Inject
    public SerialResource(SseBroadcaster broadcaster){
        this.broadcaster = broadcaster;
    }

    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput subscribeSerial(){
        final EventOutput eventOutput = new EventOutput();
        this.broadcaster.add(eventOutput);
        return eventOutput;
    }
}

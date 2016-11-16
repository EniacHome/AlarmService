package com.eniacdevelopment.EniacHome.Resources;

import org.elasticsearch.client.transport.TransportClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {
    private final TransportClient transportClient;

    @Inject
    public MyResource(TransportClient transportClient) {
        this.transportClient = transportClient;
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        if(this.transportClient == null)
            return "null";
        else
            return "not null";
    }
}

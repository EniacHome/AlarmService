package com.eniacdevelopment.EniacHome;

import com.eniacdevelopment.EniacHome.Application.Main;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.media.sse.SseFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * Created by larsg on 11/26/2016.
 */
public class UnitTestShared {
    public static HttpServer getServer(){
        return Main.startServer();
    }

    public static WebTarget getWebTarger(){
        // create the client
        Client c = ClientBuilder.newClient()
                .register(SseFeature.class)
                .register(JacksonJsonProvider.class);

        return c.target(Main.BASE_URI);
    }
}

package com.eniacdevelopment.EniacHome.Application;

import com.eniacdevelopment.EniacHome.Binding.Factory.PropertiesFactoryBinder;
import com.eniacdevelopment.EniacHome.Binding.MainBinder;
import com.eniacdevelopment.EniacHome.Features.AuthenticationFilter;
import com.eniacdevelopment.EniacHome.Features.ImmediateFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.utils.Exceptions;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;

/**
 * Main class.
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI_KEY = "BASE_URI";
    public static String BASE_URI = "http://localhost:9090/service";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     *
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.eniacdevelopment package
        final ResourceConfig resourceConfig = new ResourceConfig()
                .packages("com.eniacdevelopment.EniacHome.Resources")
                .register(ImmediateFeature.class)
                .register(AuthenticationFilter.class)
                .register(RolesAllowedDynamicFeature.class)
                .register(new MainBinder())
                .register(JacksonJsonProvider.class)
                .register(new ExceptionMapper<Exception>() {
                    @Override
                    public Response toResponse(Exception e) {
                        e.printStackTrace();
                        return Response.status(500).entity(Exceptions.getStackTraceAsString(e)).type("text/plain")
                                .build();
                    }
                });

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), resourceConfig);
    }

    /**
     * Main method.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) {
        Properties properties = new PropertiesFactoryBinder().provide();
        BASE_URI = properties.getProperty(BASE_URI_KEY);

        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.stop();
    }
}


package com.eniacdevelopment.EniacHome.Resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by larsg on 10/13/2016.
 */
@Path("/request")
class ConfigurationResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String AlarmStatus(){
        return "Hello World";
    }
}

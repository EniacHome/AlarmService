package com.eniacdevelopment.EniacHome.Resources;

import com.eniacdevelopment.EniacHome.DataModel.Configuration.SerialConfiguration;
import com.eniacdevelopment.EniacHome.Repositories.ConfigurationRepository;
import com.eniacdevelopment.EniacHome.Serial.SerialSubject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.transport.TransportClient;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by larsg on 11/16/2016.
 */
@Path("/configuration")
public class ConfigurationResource {
    private final ConfigurationRepository configurationRepository;
    private final SerialSubject serialSubject;

    @Inject
    public ConfigurationResource(ConfigurationRepository configurationRepository, SerialSubject serialSubject){
        this.configurationRepository = configurationRepository;
        this.serialSubject = serialSubject;
    }

    //region SerialConfiguration
    /**
     * GETs all configurations from the repository.
     * @return All configurations.
     */
    @GET
    @Path("/serial")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SerialConfiguration> getSerialConfigurations(){
        return this.configurationRepository.getConfigurations(SerialConfiguration.class);
    }

    /**
     * GETs a single configuration from the repository.
     * @param id Id to match.
     * @return The configuration with matching Id.
     */
    @GET
    @Path("/serial/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public SerialConfiguration getSerialConfiguration(@PathParam("id") String id){
        return this.configurationRepository.getConfiguration(SerialConfiguration.class, id);
    }

    /**
     * GETs the currently active configuration.
     * @return The active configuration.
     */
    @GET
    @Path("/serial/active")
    @Produces(MediaType.APPLICATION_JSON)
    public SerialConfiguration getActiveSerialConfiguration(){
        return this.configurationRepository.getActiveConfiguration(SerialConfiguration.class);
    }

    /**
     * POSTs a single configuration to the repository.
     * @param serialConfiguration The configuration to post.
     */
    @POST
    @Path("/serial")
    @Consumes(MediaType.APPLICATION_JSON)
    public void postSerialConfiguration(SerialConfiguration serialConfiguration){
        this.configurationRepository.setConfiguration(serialConfiguration);
    }

    /**
     * DELETEs a single configuration from the repository.
     * @param id Id to match.
     */
    @DELETE
    @Path("/serial/{id}")
    public void deleteSerialConfiguration(@PathParam("id") String id) {
        this.configurationRepository.deleteConfiguration(SerialConfiguration.class, id);
    }
    //endregion
}

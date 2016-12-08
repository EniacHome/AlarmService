package com.eniacdevelopment.EniacHome.Binding.Factory;

import com.eniacdevelopment.EniacHome.Configuration.LocalConfiguration;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.glassfish.hk2.api.Factory;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;

/**
 * Created by larsg on 12/7/2016.
 */
public class LocalConfigurationFactory implements Factory<LocalConfiguration>{

    private final ObjectReader objectReader;
    private final ObjectWriter objectWriter;
    private final File configFile;
    private LocalConfiguration localConfiguration;

    @Inject
    public LocalConfigurationFactory(ObjectReader objectReader, ObjectWriter objectWriter){
        this.objectReader = objectReader;
        this.objectWriter = objectWriter;
        this.configFile = new File("./alarmservice.config");
    }

    @Override
    public LocalConfiguration provide() {
        try {
            this.localConfiguration = this.objectReader.forType(LocalConfiguration.class).readValue(this.configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.localConfiguration;
    }

    @Override
    public void dispose(LocalConfiguration localConfiguration) {
        try {
            this.objectWriter.withDefaultPrettyPrinter().writeValue(configFile, localConfiguration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

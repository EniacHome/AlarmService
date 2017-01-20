package com.eniacdevelopment.EniacHome.Binding.Factory;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;
import java.io.*;
import java.util.Properties;

/**
 * Created by larsg on 1/19/2017.
 */
public class PropertiesFactoryBinder extends AbstractBinder implements Factory<Properties> {
    private static final String FILENAME = "app.properties";
    private final Properties properties;

    public PropertiesFactoryBinder() {
        this.properties = new Properties();
    }

    @Override
    protected void configure() {
        bindFactory(this).to(Properties.class);
    }

    @Singleton
    @Override
    public Properties provide() {
        InputStream inputStream;
        try {
            File propertiesFile = new File(FILENAME);
            inputStream = new FileInputStream(propertiesFile);
        } catch (FileNotFoundException e) {
            inputStream = null;
        }

        if (inputStream == null) {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(FILENAME);
        }

        try {
            this.properties.load(inputStream);
        } catch (IOException e) {
        }

        return this.properties;
    }

    @Override
    public void dispose(Properties properties) {
        OutputStream outputStream = null;
        File propertiesFile = new File(FILENAME);
        try {
            outputStream = new FileOutputStream(propertiesFile);
            this.properties.store(outputStream, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

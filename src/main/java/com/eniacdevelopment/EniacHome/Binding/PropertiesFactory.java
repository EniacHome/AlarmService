package com.eniacdevelopment.EniacHome.Binding;

import org.glassfish.hk2.api.Factory;

import java.io.*;
import java.util.Properties;

/**
 * Created by larsg on 12/7/2016.
 */
public class PropertiesFactory implements Factory<Properties> {
    private final File propertiesFile;
    private final Properties properties;

    public PropertiesFactory() throws IOException {
        this.propertiesFile = new File("./alarmservice.properties"); //TODO no magic strings
        this.propertiesFile.createNewFile();
        this.properties = new Properties();
    }

    @Override
    public Properties provide() {
        try{
            FileInputStream propertiesFileInputStream = new FileInputStream(this.propertiesFile);
            this.properties.load(propertiesFileInputStream);
            propertiesFileInputStream.close();
        } catch (Exception e) {
            System.err.println("=== PROPERTIES FILE ERROR: PROVIDING ===");
            e.printStackTrace();
            System.err.println("=================================");
        }

        return this.properties;
    }

    @Override
    public void dispose(Properties properties) {
        try {
            FileOutputStream propertiesFileOutputStream = new FileOutputStream(this.propertiesFile);
            this.properties.store(propertiesFileOutputStream, "");
            propertiesFileOutputStream.close();
        } catch (IOException e) {
            System.err.println("=== PROPERTIES FILE ERROR: DISPOSING ===");
            e.printStackTrace();
            System.err.println("=================================");
        }
    }
}

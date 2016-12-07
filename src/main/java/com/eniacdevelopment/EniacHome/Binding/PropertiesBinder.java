package com.eniacdevelopment.EniacHome.Binding;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by larsg on 12/1/2016.
 */
public class PropertiesBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(PropertiesFactory.class).to(Properties.class);
    }
}

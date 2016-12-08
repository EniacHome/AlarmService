package com.eniacdevelopment.EniacHome.Binding;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Created by larsg on 11/18/2016.
 */
public class JacksonBinder extends AbstractBinder {
    @Override
    protected void configure() {
        ObjectMapper objectMapper = new ObjectMapper();

        bind(objectMapper).to(ObjectMapper.class);
        bind(objectMapper.reader()).to(ObjectReader.class);
        bind(objectMapper.writer()).to(ObjectWriter.class);
    }
}

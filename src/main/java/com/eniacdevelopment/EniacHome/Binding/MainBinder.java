package com.eniacdevelopment.EniacHome.Binding;

import com.eniacdevelopment.EniacHome.Repositories.ConfigurationRepository;
import com.eniacdevelopment.EniacHome.Serial.SerialSubject;
import org.glassfish.hk2.api.Immediate;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

/**
 * Created by larsg on 11/16/2016.
 */
public class MainBinder extends AbstractBinder {
    @Override
    protected void configure() {
        install(new JacksonBinder());
        install(new TransportClientBinder());
        install(new SerialBinder());

        bindAsContract(ConfigurationRepository.class).in(Singleton.class);
    }
}
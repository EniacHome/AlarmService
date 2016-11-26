package com.eniacdevelopment.EniacHome.Binding;

import com.eniacdevelopment.EniacHome.Repositories.ConfigurationRepository;
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

        bind(ConfigurationRepository.class).to(ConfigurationRepository.class).in(Singleton.class);
    }
}
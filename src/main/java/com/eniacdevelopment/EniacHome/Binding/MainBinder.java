package com.eniacdevelopment.EniacHome.Binding;

import com.eniacdevelopment.EniacHome.Binding.Factory.LocalConfigurationFactory;
import com.eniacdevelopment.EniacHome.Configuration.LocalConfiguration;
import com.eniacdevelopment.EniacHome.Repositories.ConfigurationRepository;
import org.glassfish.hk2.api.Immediate;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;

import javax.inject.Singleton;

/**
 * Created by larsg on 11/16/2016.
 */
public class MainBinder extends AbstractBinder {
    @Override
    protected void configure() {
        install(new JacksonBinder());
        install(new TransportClientBinder());
        install(new PacketListenerObserverBinder());
        install(new SerialBinder());

        bindFactory(LocalConfigurationFactory.class).to(LocalConfiguration.class);
        bindAsContract(ConfigurationRepository.class).in(Singleton.class);
    }
}
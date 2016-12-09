package com.eniacdevelopment.EniacHome.Binding;

import com.eniacdevelopment.EniacHome.Binding.Factory.LocalConfigurationFactory;
import com.eniacdevelopment.EniacHome.Configuration.LocalConfiguration;
import com.eniacdevelopment.EniacHome.Repositories.Shared.ConfigurationRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.UserRepository;
import com.eniacdevelopment.EniacHome.Repositories.ElasticSearch.UserRepositoryImpl;
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
        install(new PacketListenerObserverBinder());
        install(new SerialBinder());

        bindFactory(LocalConfigurationFactory.class).to(LocalConfiguration.class);

        bind(UserRepositoryImpl.class).to(UserRepository.class);
        bindAsContract(ConfigurationRepository.class).in(Singleton.class);
    }
}
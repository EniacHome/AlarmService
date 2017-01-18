package com.eniacdevelopment.EniacHome.Binding;

import com.eniacdevelopment.EniacHome.DataModel.Configuration.SerialConfiguration;
import com.eniacdevelopment.EniacHome.Repositories.ElasticSearch.SerialConfigurationRepository;
import com.eniacdevelopment.EniacHome.Repositories.ElasticSearch.TokenRepositoryImpl;
import com.eniacdevelopment.EniacHome.Repositories.ElasticSearch.UserRepositoryImpl;
import com.eniacdevelopment.EniacHome.Repositories.Shared.ConfigurationRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.TokenRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.UserRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.Utils.TokenUtils;
import com.eniacdevelopment.EniacHome.Repositories.Shared.Utils.UserUtils;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Created by larsg on 11/16/2016.
 */
public class MainBinder extends AbstractBinder {
    @Override
    protected void configure() {
        install(new JacksonBinder());
        install(new TransportClientBinder());
        install(new EventListenerObserverBinder());
        install(new SerialBinder());
        install(new SensorBinder());
        install(new AlarmBinder());
        install(new ServiceBinder());

        bind(UserRepositoryImpl.class).to(UserRepository.class);
        bind(TokenRepositoryImpl.class).to(TokenRepository.class);
        bind(TokenUtils.class).to(TokenUtils.class);
        bind(UserUtils.class).to(UserUtils.class);
        bind(SerialConfigurationRepository.class).to(new TypeLiteral<ConfigurationRepository<SerialConfiguration>>() {
        });
    }
}
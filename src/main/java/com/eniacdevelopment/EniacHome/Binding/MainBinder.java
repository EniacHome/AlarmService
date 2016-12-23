package com.eniacdevelopment.EniacHome.Binding;

import com.eniacdevelopment.EniacHome.Binding.Factory.LocalConfigurationFactory;
import com.eniacdevelopment.EniacHome.Configuration.LocalConfiguration;
import com.eniacdevelopment.EniacHome.DataModel.Configuration.Configuration;
import com.eniacdevelopment.EniacHome.DataModel.Configuration.SerialConfiguration;
import com.eniacdevelopment.EniacHome.Repositories.ElasticSearch.ConfigurationRepositoryImpl;
import com.eniacdevelopment.EniacHome.Repositories.ElasticSearch.SerialConfigurationRepository;
import com.eniacdevelopment.EniacHome.Repositories.ElasticSearch.TokenRepositoryImpl;
import com.eniacdevelopment.EniacHome.Repositories.Shared.ConfigurationRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.TokenRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.UserRepository;
import com.eniacdevelopment.EniacHome.Repositories.ElasticSearch.UserRepositoryImpl;
import com.eniacdevelopment.EniacHome.Repositories.Shared.Utils.TokenUtils;
import com.eniacdevelopment.EniacHome.Repositories.Shared.Utils.UserUtils;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.BuilderHelper;
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
        bind(TokenRepositoryImpl.class).to(TokenRepository.class);
        bind(TokenUtils.class).to(TokenUtils.class);
        bind(UserUtils.class).to(UserUtils.class);
        bind(SerialConfigurationRepository.class).to(new TypeLiteral<ConfigurationRepository<SerialConfiguration>>() {});
    }
}
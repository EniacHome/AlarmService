package com.eniacdevelopment.EniacHome.Binding;

import com.eniacdevelopment.EniacHome.DataModel.Alarm.AlarmStatus;
import com.eniacdevelopment.EniacHome.Repositories.ElasticSearch.AlarmEventRepositoryImpl;
import com.eniacdevelopment.EniacHome.Repositories.InMemory.AlarmStatusRepositoryImpl;
import com.eniacdevelopment.EniacHome.Repositories.Shared.AlarmEventRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.AlarmStatusRepository;
import org.elasticsearch.common.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Created by larsg on 1/6/2017.
 */
public class AlarmBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(AlarmStatus.class).to(AlarmStatus.class).named(AlarmStatusRepositoryImpl.ALARM_STATUS_NAME).in(Singleton.class);
        bind(AlarmStatusRepositoryImpl.class).to(AlarmStatusRepository.class);

        bind(AlarmEventRepositoryImpl.class).to(AlarmEventRepository.class);
    }
}

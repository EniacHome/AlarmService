package com.eniacdevelopment.EniacHome.Binding;

import com.eniacdevelopment.EniacHome.Business.Contracts.Utils.AlarmCalculator;
import com.eniacdevelopment.EniacHome.Business.Utils.AlarmCalculatorImpl;
import com.eniacdevelopment.EniacHome.DataModel.Alarm.AlarmStatus;
import com.eniacdevelopment.EniacHome.Repositories.ElasticSearch.AlarmEventRepositoryImpl;
import com.eniacdevelopment.EniacHome.Repositories.InMemory.AlarmStatusRepositoryImpl;
import com.eniacdevelopment.EniacHome.Repositories.Shared.AlarmEventRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.AlarmStatusRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

/**
 * Created by larsg on 1/6/2017.
 */
public class AlarmBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(AlarmCalculatorImpl.class).to(AlarmCalculator.class);

        bind(AlarmStatus.class).to(AlarmStatus.class).named(AlarmStatusRepositoryImpl.ALARM_STATUS_NAME).in(Singleton.class);
        bind(AlarmStatusRepositoryImpl.class).to(AlarmStatusRepository.class);

        bind(AlarmEventRepositoryImpl.class).to(AlarmEventRepository.class);
    }
}

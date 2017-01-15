package com.eniacdevelopment.EniacHome.Binding;

import com.eniacdevelopment.EniacHome.Business.AlarmServiceImpl;
import com.eniacdevelopment.EniacHome.Business.Contracts.AlarmService;
import com.eniacdevelopment.EniacHome.Business.Contracts.SensorService;
import com.eniacdevelopment.EniacHome.Business.Contracts.UserService;
import com.eniacdevelopment.EniacHome.Business.SensorServiceImpl;
import com.eniacdevelopment.EniacHome.Business.UserServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Created by larsg on 1/13/2017.
 */
public class ServiceBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(AlarmServiceImpl.class).to(AlarmService.class);
        bind(SensorServiceImpl.class).to(SensorService.class);
        bind(UserServiceImpl.class).to(UserService.class);
    }
}

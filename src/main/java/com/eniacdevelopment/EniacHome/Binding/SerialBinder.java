package com.eniacdevelopment.EniacHome.Binding;

import com.eniacdevelopment.EniacHome.Serial.SerialSubject;
import org.glassfish.hk2.api.Immediate;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by larsg on 11/20/2016.
 */
public class SerialBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(SerialSubject.class).to(SerialSubject.class).in(Immediate.class);
    }
}
package com.eniacdevelopment.EniacHome.Binding;

import com.eniacdevelopment.EniacHome.Serial.SerialSubject;
import org.glassfish.hk2.api.Immediate;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.media.sse.SseBroadcaster;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by larsg on 11/20/2016.
 */
public class SerialBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(SseBroadcaster.class).to(SseBroadcaster.class).in(Singleton.class);
        bind(SerialSubject.class).to(SerialSubject.class).in(Singleton.class);

        //The factory is used to immediately add all PacketListenerObservers to the already Singleton SerialSubject.
        bindFactory(SerialSubjectFactory.class).to(SerialSubject.class).in(Immediate.class);
    }
}
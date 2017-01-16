package com.eniacdevelopment.EniacHome.Binding;

import com.eniacdevelopment.EniacHome.Binding.Factory.SerialSubjectFactory;
import com.eniacdevelopment.EniacHome.Serial.PacketParsers.PacketParser;
import com.eniacdevelopment.EniacHome.Serial.PacketParsers.PacketParserImpl;
import com.eniacdevelopment.EniacHome.Serial.SerialSubject;
import org.glassfish.hk2.api.Immediate;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.media.sse.SseBroadcaster;

import javax.inject.Singleton;

/**
 * Created by larsg on 11/20/2016.
 */
public class SerialBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(SseBroadcaster.class).to(SseBroadcaster.class).in(Singleton.class);
        bind(PacketParserImpl.class).to(PacketParser.class);
        bind(SerialSubject.class).to(SerialSubject.class).in(Singleton.class);

        //The factory is used to immediately add all EventListenerObservers to the already Singleton SerialSubject.
        bindFactory(SerialSubjectFactory.class).to(SerialSubject.class).in(Immediate.class);
    }
}
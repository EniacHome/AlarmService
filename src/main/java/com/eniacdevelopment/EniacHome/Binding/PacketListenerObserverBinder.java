package com.eniacdevelopment.EniacHome.Binding;

import com.eniacdevelopment.EniacHome.Serial.PacketListenerObservers.ESPacketListenerObserver;
import com.eniacdevelopment.EniacHome.Serial.PacketListenerObservers.JerseyPacketListenerObserver;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Created by larsg on 12/7/2016.
 */
public class PacketListenerObserverBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(ESPacketListenerObserver.class).to(ESPacketListenerObserver.class);
        bind(JerseyPacketListenerObserver.class).to(JerseyPacketListenerObserver.class);
    }
}

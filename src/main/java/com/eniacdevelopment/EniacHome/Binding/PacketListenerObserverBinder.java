package com.eniacdevelopment.EniacHome.Binding;

import com.eniacdevelopment.EniacHome.Serial.PacketListenerObservers.JerseyPacketListenerObserver;
import com.eniacdevelopment.EniacHome.Serial.PacketListenerObservers.SensorEventPacketListenerObserver;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Created by larsg on 12/7/2016.
 */
public class PacketListenerObserverBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(SensorEventPacketListenerObserver.class).to(SensorEventPacketListenerObserver.class);
        bind(JerseyPacketListenerObserver.class).to(JerseyPacketListenerObserver.class);
    }
}

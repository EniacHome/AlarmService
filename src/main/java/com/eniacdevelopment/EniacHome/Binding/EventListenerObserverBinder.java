package com.eniacdevelopment.EniacHome.Binding;

import com.eniacdevelopment.EniacHome.Serial.EventListenerObservers.JerseyEventListenerObserver;
import com.eniacdevelopment.EniacHome.Serial.EventListenerObservers.SensorEventEventListenerObserver;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Created by larsg on 12/7/2016.
 */
public class EventListenerObserverBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(SensorEventEventListenerObserver.class).to(SensorEventEventListenerObserver.class);
        bind(JerseyEventListenerObserver.class).to(JerseyEventListenerObserver.class);
    }
}

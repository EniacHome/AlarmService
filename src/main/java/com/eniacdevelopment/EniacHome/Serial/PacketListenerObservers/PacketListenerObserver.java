package com.eniacdevelopment.EniacHome.Serial.PacketListenerObservers;

import com.eniacdevelopment.EniacHome.Serial.Objects.SensorNotification;

/**
 * Created by larsg on 9/16/2016.
 */
public abstract class PacketListenerObserver  {
    public abstract void eventNotify(SensorNotification sensorNotification);
}

package com.eniacdevelopment.EniacHome.Serial.PacketListenerObservers;

/**
 * Created by larsg on 9/16/2016.
 */
public abstract class PacketListenerObserver  {
    public abstract void eventNotify(String sensorId);
}

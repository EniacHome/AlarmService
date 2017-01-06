package com.eniacdevelopment.EniacHome.Binding.Factory;

import com.eniacdevelopment.EniacHome.Configuration.LocalConfiguration;
import com.eniacdevelopment.EniacHome.Configuration.PacketListenerObserverConfiguration;
import com.eniacdevelopment.EniacHome.Serial.PacketListenerObservers.PacketListenerObserver;
import com.eniacdevelopment.EniacHome.Serial.SerialSubject;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.reflections.Reflections;

import javax.inject.Inject;
import java.util.Set;

/**
 * Created by larsg on 11/20/2016.
 * The SerialSubjectFactory is used to add all PacketListenerObservers to the SerialSubject.
 */
public class SerialSubjectFactory implements Factory<SerialSubject> {

    private final ServiceLocator serviceLocator;
    private final SerialSubject serialSubject;

    @Inject
    public SerialSubjectFactory(ServiceLocator serviceLocator, SerialSubject serialSubject){
        this.serviceLocator = serviceLocator;
        this.serialSubject = serialSubject;
    }

    @Override
    public SerialSubject provide() {
        this.serialSubject.initializeSerial();

        PacketListenerObserverConfiguration configuration = serviceLocator.getService(LocalConfiguration.class).packetListenerObserverConfiguration;

        //Get all types that extend PacketListenerObserver
        Reflections reflections = new Reflections("com.eniacdevelopment.EniacHome.Serial.PacketListenerObservers");
        Set<Class<? extends PacketListenerObserver>> observerClasses = reflections.getSubTypesOf(PacketListenerObserver.class);

        //Iterate all types and try to instantiate it using the ServiceLocator
        for (Class<? extends PacketListenerObserver> observerClass : observerClasses) {
            //Get the observer from the ServiceLocator
            PacketListenerObserver observer = this.serviceLocator.getService(observerClass);
            if((observer != null) &&
                    !(configuration.PacketListenerObservers.containsKey(observerClass.getName()) &&
                            !configuration.PacketListenerObservers.get(observerClass.getName()))) {
                //Only add the observer if it is found in the ServiceLocator and if it is enabled in config
                serialSubject.addObserver(observer);
            }
        }

        return this.serialSubject;
    }

    @Override
    public void dispose(SerialSubject serialSubject) {
    }
}

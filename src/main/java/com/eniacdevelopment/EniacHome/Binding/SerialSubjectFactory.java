package com.eniacdevelopment.EniacHome.Binding;

import com.eniacdevelopment.EniacHome.Serial.SerialSubject;
import org.glassfish.hk2.api.Factory;

/**
 * Created by larsg on 11/20/2016.
 */
public class SerialSubjectFactory implements Factory<SerialSubject> {
    @Override
    public SerialSubject provide() {
        return null;
    }

    @Override
    public void dispose(SerialSubject serialSubject) {
    }
}

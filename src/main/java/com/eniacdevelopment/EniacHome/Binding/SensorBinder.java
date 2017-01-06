package com.eniacdevelopment.EniacHome.Binding;

import com.eniacdevelopment.EniacHome.DataModel.Sensor.SensorStatus;
import com.eniacdevelopment.EniacHome.Repositories.ElasticSearch.SensorEventRepositoryImpl;
import com.eniacdevelopment.EniacHome.Repositories.ElasticSearch.SensorRepositoryImpl;
import com.eniacdevelopment.EniacHome.Repositories.InMemory.SensorStatusRepositoryImpl;
import com.eniacdevelopment.EniacHome.Repositories.Shared.SensorEventRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.SensorRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.SensorStatusRepository;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by larsg on 1/5/2017.
 */
public class SensorBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(SensorEventRepositoryImpl.class).to(SensorEventRepository.class);
        bind(SensorStatusRepositoryImpl.class).to(SensorStatusRepository.class);
        bind(SensorRepositoryImpl.class).to(SensorRepository.class);

        bind(new HashMap<String, SensorStatus>()).to(new TypeLiteral<Map<String, SensorStatus>>() {
        })
                .named(SensorStatusRepositoryImpl.SENSOR_STATUS_DICTIONARY_NAME);
    }
}

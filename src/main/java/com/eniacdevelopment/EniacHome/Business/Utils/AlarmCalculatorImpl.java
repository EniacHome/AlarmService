package com.eniacdevelopment.EniacHome.Business.Utils;

import com.eniacdevelopment.EniacHome.Business.Contracts.Utils.AlarmCalculator;
import com.eniacdevelopment.EniacHome.DataModel.Alarm.AlarmStatus;
import com.eniacdevelopment.EniacHome.DataModel.Sensor.Sensor;
import com.eniacdevelopment.EniacHome.Repositories.Shared.AlarmStatusRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.SensorRepository;
import com.eniacdevelopment.EniacHome.Serial.Objects.SensorNotification;

import javax.inject.Inject;

/**
 * Created by larsg on 1/7/2017.
 */
public class AlarmCalculatorImpl implements AlarmCalculator {

    private final AlarmStatusRepository alarmStatusRepository;
    private final SensorRepository sensorRepository;

    @Inject
    public AlarmCalculatorImpl(AlarmStatusRepository alarmStatusRepository, SensorRepository sensorRepository) {
        this.alarmStatusRepository = alarmStatusRepository;
        this.sensorRepository = sensorRepository;
    }

    @Override
    public boolean calculate(SensorNotification sensorNotification) {
        AlarmStatus alarmStatus = this.alarmStatusRepository.getAlarmStatus();
        Sensor sensor = this.sensorRepository.get(sensorNotification.Id);

        if (!alarmStatus.Enabled || !sensor.Enabled) {
            return false;
        }

        if (alarmStatus.Level > sensor.Level) {
            return false;
        }

        switch (sensor.CompareMethod) {
            case Equal:
                return sensor.CompareValue == sensorNotification.Value;
            case Greater:
                return sensor.CompareValue > sensorNotification.Value;
            case Smaller:
                return sensor.CompareValue < sensorNotification.Value;
            case GreaterOrEqual:
                return sensor.CompareValue >= sensorNotification.Value;
            case SmallerOrEqual:
                return sensor.CompareValue <= sensorNotification.Value;
            default:
                return false;
        }
    }
}

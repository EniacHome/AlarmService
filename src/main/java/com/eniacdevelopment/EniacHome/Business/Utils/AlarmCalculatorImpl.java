package com.eniacdevelopment.EniacHome.Business.Utils;

import com.eniacdevelopment.EniacHome.Business.Contracts.SensorService;
import com.eniacdevelopment.EniacHome.Business.Contracts.Utils.AlarmCalculator;
import com.eniacdevelopment.EniacHome.DataModel.Alarm.AlarmStatus;
import com.eniacdevelopment.EniacHome.DataModel.Sensor.CompareMethod;
import com.eniacdevelopment.EniacHome.DataModel.Sensor.Sensor;
import com.eniacdevelopment.EniacHome.Repositories.Shared.AlarmStatusRepository;
import com.eniacdevelopment.EniacHome.Serial.Objects.SensorNotification;

import javax.inject.Inject;

/**
 * Created by larsg on 1/7/2017.
 */
public class AlarmCalculatorImpl implements AlarmCalculator {

    private final AlarmStatusRepository alarmStatusRepository;
    private final SensorService sensorService;

    @Inject
    public AlarmCalculatorImpl(AlarmStatusRepository alarmStatusRepository, SensorService sensorService) {
        this.alarmStatusRepository = alarmStatusRepository;
        this.sensorService = sensorService;
    }

    @Override
    public boolean calculate(SensorNotification sensorNotification) {
        AlarmStatus alarmStatus = this.alarmStatusRepository.getAlarmStatus();
        Sensor sensor = this.sensorService.getSensor(sensorNotification.Id);

        if (sensor == null) {
            return false;
        }

        if (!alarmStatus.Enabled || !sensor.Enabled) {
            return false;
        }

        if (alarmStatus.Level > sensor.Level) {
            return false;
        }

        return compare(sensor.CompareMethod, sensor.CompareValue, sensorNotification.Value);
    }

    @Override
    public boolean calculate(String sensorId) {
        AlarmStatus alarmStatus = this.alarmStatusRepository.getAlarmStatus();
        Sensor sensor = this.sensorService.getSensor(sensorId);

        if (sensor == null) {
            return false;
        }

        if (!alarmStatus.Enabled || !sensor.Enabled) {
            return false;
        }

        if (alarmStatus.Level > sensor.Level) {
            return false;
        }

        return compare(sensor.CompareMethod, sensor.CompareValue, sensor.SensorStatus.Value);
    }

    private boolean compare(CompareMethod compareMethod, int compareValue, int value) {
        switch (compareMethod) {
            case Equal:
                return compareValue == value;
            case Greater:
                return compareValue > value;
            case Smaller:
                return compareValue < value;
            case GreaterOrEqual:
                return compareValue >= value;
            case SmallerOrEqual:
                return compareValue <= value;
            default:
                return false;
        }
    }

}

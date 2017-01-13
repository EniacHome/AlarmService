package com.eniacdevelopment.EniacHome.Business.Contracts.Utils;

import com.eniacdevelopment.EniacHome.Serial.Objects.SensorNotification;

/**
 * Created by larsg on 1/7/2017.
 */
public interface AlarmCalculator {
    boolean calculate(SensorNotification sensorNotification);
}
package com.eniacdevelopment.EniacHome.Business.Contracts;

import com.eniacdevelopment.EniacHome.DataModel.Alarm.AlarmStatus;

/**
 * Created by larsg on 1/13/2017.
 */
public interface AlarmService {
    void enableAlarm(int level);

    void disableAlarm();

    AlarmStatus getAlarmStatus();
}

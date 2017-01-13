package com.eniacdevelopment.EniacHome.Repositories.Shared;

import com.eniacdevelopment.EniacHome.DataModel.Alarm.AlarmStatus;

/**
 * Created by larsg on 1/6/2017.
 */
public interface AlarmStatusRepository {

    AlarmStatus getAlarmStatus();

    void setAlarmStatus(AlarmStatus alarmStatus);
}

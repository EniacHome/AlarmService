package com.eniacdevelopment.EniacHome.Repositories.InMemory;

import com.eniacdevelopment.EniacHome.DataModel.Alarm.AlarmStatus;
import com.eniacdevelopment.EniacHome.Repositories.Shared.AlarmStatusRepository;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by larsg on 1/6/2017.
 */
public class AlarmStatusRepositoryImpl implements AlarmStatusRepository {
    public static final String ALARM_STATUS_NAME = "Alarm_Status";
    private AlarmStatus alarmStatus;

    @Inject
    public AlarmStatusRepositoryImpl(@Named(ALARM_STATUS_NAME) AlarmStatus alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    @Override
    public AlarmStatus getAlarmStatus() {
        return this.alarmStatus;
    }

    @Override
    public void setAlarmStatus(AlarmStatus alarmStatus) {
        this.alarmStatus = alarmStatus;
    }
}

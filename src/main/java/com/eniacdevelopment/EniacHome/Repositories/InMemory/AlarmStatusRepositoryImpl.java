package com.eniacdevelopment.EniacHome.Repositories.InMemory;

import com.eniacdevelopment.EniacHome.DataModel.AlarmStatus;
import com.eniacdevelopment.EniacHome.Repositories.Shared.AlarmStatusRepository;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by larsg on 1/6/2017.
 */
public class AlarmStatusRepositoryImpl implements AlarmStatusRepository {
    public static final String ALARM_STATUS_NAME = "Alarm_Status";
    private final AlarmStatus alarmStatus;

    @Inject
    public AlarmStatusRepositoryImpl(@Named(ALARM_STATUS_NAME) AlarmStatus alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    @Override
    public AlarmStatus getAlarmStatus() {
        return this.alarmStatus;
    }

    @Override
    public void enableAlarm(int level) {
        this.alarmStatus.Level = level;
        this.alarmStatus.Enabled = true;
    }

    @Override
    public void disableAlarm() {
        this.alarmStatus.Enabled = false;
    }
}

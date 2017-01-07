package com.eniacdevelopment.EniacHome.Repositories.InMemory;

import com.eniacdevelopment.EniacHome.DataModel.Alarm.AlarmEvent;
import com.eniacdevelopment.EniacHome.DataModel.Alarm.AlarmStatus;
import com.eniacdevelopment.EniacHome.Repositories.Shared.AlarmEventRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.AlarmStatusRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;

/**
 * Created by larsg on 1/6/2017.
 */
public class AlarmStatusRepositoryImpl implements AlarmStatusRepository {
    public static final String ALARM_STATUS_NAME = "Alarm_Status";
    private final AlarmStatus alarmStatus;
    private final AlarmEventRepository alarmEventRepository;

    @Inject
    public AlarmStatusRepositoryImpl(@Named(ALARM_STATUS_NAME) AlarmStatus alarmStatus, AlarmEventRepository alarmEventRepository) {
        this.alarmStatus = alarmStatus;
        this.alarmEventRepository = alarmEventRepository;
    }

    @Override
    public AlarmStatus getAlarmStatus() {
        return this.alarmStatus;
    }

    @Override
    public void enableAlarm(int level) {
        this.alarmStatus.Level = level;
        this.alarmStatus.Enabled = true;

        AlarmEvent alarmEvent = new AlarmEvent() {{
            Enabled = true;
            Level = level;
            Date = new Date();
        }};

        this.alarmEventRepository.add(alarmEvent);
    }

    @Override
    public void disableAlarm() {
        this.alarmStatus.Enabled = false;
        this.alarmStatus.Level = Integer.MAX_VALUE;

        AlarmEvent alarmEvent = new AlarmEvent() {{
            Enabled = false;
            Level = Integer.MAX_VALUE;
            Date = new Date();
        }};

        this.alarmEventRepository.add(alarmEvent);
    }
}

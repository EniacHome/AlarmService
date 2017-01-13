package com.eniacdevelopment.EniacHome.Business;

import com.eniacdevelopment.EniacHome.Business.Contracts.AlarmService;
import com.eniacdevelopment.EniacHome.DataModel.Alarm.AlarmEvent;
import com.eniacdevelopment.EniacHome.DataModel.Alarm.AlarmStatus;
import com.eniacdevelopment.EniacHome.Repositories.Shared.AlarmEventRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.AlarmStatusRepository;

import javax.inject.Inject;
import java.util.Date;

/**
 * Created by larsg on 1/13/2017.
 */
public class AlarmServiceImpl implements AlarmService {

    private final AlarmStatusRepository alarmStatusRepository;
    private final AlarmEventRepository alarmEventRepository;

    @Inject
    public AlarmServiceImpl(AlarmStatusRepository alarmStatusRepository, AlarmEventRepository alarmEventRepository) {
        this.alarmStatusRepository = alarmStatusRepository;
        this.alarmEventRepository = alarmEventRepository;
    }

    public void enableAlarm(int level) {
        AlarmStatus alarmStatus = new AlarmStatus() {{
            Enabled = true;
            Level = level;
        }};
        this.alarmStatusRepository.setAlarmStatus(alarmStatus);

        AlarmEvent alarmEvent = new AlarmEvent() {{
            Enabled = true;
            Level = level;
            Date = new Date();
        }};
        this.alarmEventRepository.add(alarmEvent);
    }

    public void disableAlarm() {
        AlarmStatus alarmStatus = new AlarmStatus() {{
            Enabled = false;
            Level = Integer.MAX_VALUE;
        }};
        this.alarmStatusRepository.setAlarmStatus(alarmStatus);

        AlarmEvent alarmEvent = new AlarmEvent() {{
            Enabled = false;
            Level = Integer.MAX_VALUE;
            Date = new Date();
        }};
        this.alarmEventRepository.add(alarmEvent);
    }

    public AlarmStatus getAlarmStatus() {
        return this.alarmStatusRepository.getAlarmStatus();
    }
}

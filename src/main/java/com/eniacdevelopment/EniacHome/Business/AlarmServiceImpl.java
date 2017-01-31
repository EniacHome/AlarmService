package com.eniacdevelopment.EniacHome.Business;

import com.eniacdevelopment.EniacHome.Business.Contracts.AlarmService;
import com.eniacdevelopment.EniacHome.Business.Contracts.Utils.AlarmCalculator;
import com.eniacdevelopment.EniacHome.DataModel.Alarm.AlarmEvent;
import com.eniacdevelopment.EniacHome.DataModel.Alarm.AlarmStatus;
import com.eniacdevelopment.EniacHome.DataModel.Sensor.SensorStatus;
import com.eniacdevelopment.EniacHome.Repositories.Shared.AlarmEventRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.AlarmStatusRepository;
import com.eniacdevelopment.EniacHome.Repositories.Shared.SensorStatusRepository;
import com.eniacdevelopment.EniacHome.Serial.SerialSubject;

import javax.inject.Inject;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by larsg on 1/13/2017.
 */
public class AlarmServiceImpl implements AlarmService {

    private final SerialSubject serialSubject;
    private final AlarmStatusRepository alarmStatusRepository;
    private final AlarmEventRepository alarmEventRepository;
    private final SensorStatusRepository sensorStatusRepository;
    private final AlarmCalculator alarmCalculator;

    @Inject
    public AlarmServiceImpl(
            SerialSubject serialSubject,
            AlarmStatusRepository alarmStatusRepository,
            AlarmEventRepository alarmEventRepository,
            SensorStatusRepository sensorStatusRepository,
            AlarmCalculator alarmCalculator) {
        this.serialSubject = serialSubject;
        this.alarmStatusRepository = alarmStatusRepository;
        this.alarmEventRepository = alarmEventRepository;
        this.sensorStatusRepository = sensorStatusRepository;
        this.alarmCalculator = alarmCalculator;
    }

    public void enableAlarm(int level) {
        AlarmStatus alarmStatus = new AlarmStatus() {{
            Enabled = true;
            Level = level;
        }};
        this.alarmStatusRepository.setAlarmStatus(alarmStatus);

        Set<Map.Entry<String, SensorStatus>> sensorStatuses = this.sensorStatusRepository.getAll();

        final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(() -> {
            for (Map.Entry<String, SensorStatus> statusEntry : sensorStatuses) {
                if (this.alarmCalculator.calculate(statusEntry.getKey())) {
                    statusEntry.getValue().Alarmed = true;
                    this.serialSubject.triggerEvent(statusEntry.getKey());
                }
            }
        }, 10, TimeUnit.SECONDS);   //TODO Delay should come from es configuration
        
        AlarmEvent alarmEvent = new AlarmEvent() {{
            Enabled = true;
            Level = level;
            Date = new Date();
        }};
        this.alarmEventRepository.add(alarmEvent);
    }

    public void disableAlarm() {
        this.serialSubject.stopAlarm();

        Set<Map.Entry<String, SensorStatus>> sensorStatuses = this.sensorStatusRepository.getAll();
        for (Map.Entry<String, SensorStatus> statusEntry : sensorStatuses) {
            if (statusEntry.getValue().Alarmed) {
                statusEntry.getValue().Alarmed = false;
                this.serialSubject.triggerEvent(statusEntry.getKey());
            }
        }

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

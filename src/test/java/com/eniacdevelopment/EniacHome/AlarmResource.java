package com.eniacdevelopment.EniacHome;

import com.eniacdevelopment.EniacHome.DataModel.Alarm.AlarmStatus;
import com.eniacdevelopment.EniacHome.Repositories.Shared.AlarmStatusRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by larsg on 1/7/2017.
 */
@Path("alarm")
public class AlarmResource {

    private final AlarmStatusRepository alarmStatusRepository;

    @Inject
    public AlarmResource(AlarmStatusRepository alarmStatusRepository) {
        this.alarmStatusRepository = alarmStatusRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public AlarmStatus getAlarmStatus() {
        return this.alarmStatusRepository.getAlarmStatus();
    }

    @GET
    @Path("enable/{level}")
    public void enableAlarm(@PathParam("level") int level) {
        this.alarmStatusRepository.enableAlarm(level);
    }

    @GET
    @Path("disable")
    public void disableAlarm() {
        this.alarmStatusRepository.disableAlarm();
    }

}

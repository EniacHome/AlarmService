package com.eniacdevelopment.EniacHome.Resources;

import com.eniacdevelopment.EniacHome.Business.Contracts.AlarmService;
import com.eniacdevelopment.EniacHome.DataModel.Alarm.AlarmStatus;

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

    private final AlarmService alarmService;

    @Inject
    public AlarmResource(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public AlarmStatus getAlarmStatus() {
        return this.alarmService.getAlarmStatus();
    }

    @GET
    @Path("enable/{level}")
    public void enableAlarm(@PathParam("level") int level) {
        this.alarmService.enableAlarm(level);
    }

    @GET
    @Path("disable")
    public void disableAlarm() {
        this.alarmService.disableAlarm();
    }
}

package com.eniacdevelopment.EniacHome.Repositories.Shared;

import com.eniacdevelopment.EniacHome.DataModel.Alarm.AlarmEvent;

/**
 * Created by larsg on 1/6/2017.
 */
public interface AlarmEventRepository {
    void add(AlarmEvent event);

    Iterable<AlarmEvent> get();
}

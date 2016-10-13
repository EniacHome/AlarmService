package com.eniacdevelopment.Application;

import com.eniacdevelopment.Controller.RequestController;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by larsg on 10/13/2016.
 */
public class MainWEB extends Application {
    @Override
    public Set<Class<?>> getClasses(){
        HashSet h = new HashSet<Class<?>>();
        h.add(RequestController.class);
        return h;
    }
}

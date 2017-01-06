package com.eniacdevelopment.EniacHome.Application;

import com.eniacdevelopment.EniacHome.Configuration.LocalConfiguration;
import com.eniacdevelopment.EniacHome.Configuration.PacketListenerObserverConfiguration;
import com.eniacdevelopment.EniacHome.Configuration.TransportClientConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by larsg on 12/8/2016.
 */
public class StubLocalMain {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("./alarmservice.config");

        LocalConfiguration localConfiguration = new LocalConfiguration(){{
            transportClientConfiguration = new TransportClientConfiguration(){{
                transportAddresses = new ArrayList<InetSocketAddress>() {{
                    add(new InetSocketAddress("localhost", 9300));
                }};
            }};

            packetListenerObserverConfiguration = new PacketListenerObserverConfiguration(){{
                PacketListenerObservers = new HashMap<String, Boolean>() {{
                    put("com.eniacdevelopment.EniacHome.Serial.PacketListenerObservers.SensorEventPacketListenerObserver", false);
                    put("com.eniacdevelopment.EniacHome.Serial.PacketListenerObservers.JerseyPacketListenerObserver", true);
                }};
            }};
        }};

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, localConfiguration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.eniacdevelopment.EniacHome.Serial.PacketParsers;

import com.eniacdevelopment.EniacHome.Serial.Objects.SensorNotification;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.util.Date;

/**
 * Created by larsg on 1/4/2017.
 */
public class PacketParserImpl implements PacketParser {
    @Override
    public SensorNotification parse(byte[] packetInfo, byte[] packetValue, SerialPortEvent event) {
        SensorNotification notification = new SensorNotification();

        notification.Id = Integer.toString((packetInfo[0] & 0xFF) >> 1);
        Boolean extended = ((packetInfo[0] & 0xFF) & 1) == 1;

        if(extended) {
            notification.Value = (packetValue[0] & 0xFF) | ((packetValue[1] & 0xFF) << 8);
        }
        else {
            notification.Value = packetValue[0] & 0xFF;
        }

        notification.Date = new Date();

        return notification;
    }
}

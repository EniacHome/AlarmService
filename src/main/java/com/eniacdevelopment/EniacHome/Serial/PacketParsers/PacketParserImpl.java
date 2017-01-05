package com.eniacdevelopment.EniacHome.Serial.PacketParsers;

import com.eniacdevelopment.EniacHome.Serial.Objects.SerialNotification;
import com.fazecast.jSerialComm.SerialPortEvent;

/**
 * Created by larsg on 1/4/2017.
 */
public class PacketParserImpl implements PacketParser {
    @Override
    public SerialNotification parse(byte[] packet, SerialPortEvent event) {
        SerialNotification notification = new SerialNotification();

        notification.Id = Integer.toString((packet[0] & 0xFF) >> 1);
        Boolean extended = ((packet[0] & 0xFF) | 1) == 1;

        if(extended) {
            byte[] extended_value = null;
            event.getSerialPort().readBytes(extended_value, 1);
            notification.Value = packet[1] | (extended_value[0] << 8);
        }
        else {
            notification.Value = packet[1];
        }

        return notification;
    }
}

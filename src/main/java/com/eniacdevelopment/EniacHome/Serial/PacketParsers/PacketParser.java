package com.eniacdevelopment.EniacHome.Serial.PacketParsers;

import com.eniacdevelopment.EniacHome.Serial.Objects.SerialNotification;
import com.fazecast.jSerialComm.SerialPortEvent;

/**
 * Created by larsg on 1/4/2017.
 */
public interface PacketParser {
    SerialNotification parse(byte[] packet, SerialPortEvent event);
}

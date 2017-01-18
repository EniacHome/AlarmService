package com.eniacdevelopment.EniacHome.Serial.PacketParsers;

import com.eniacdevelopment.EniacHome.Serial.Objects.SensorNotification;
import com.fazecast.jSerialComm.SerialPortEvent;

/**
 * Created by larsg on 1/4/2017.
 */
public interface PacketParser {
    SensorNotification parse(byte[] packetInfo, byte[] packetValue, SerialPortEvent event);
}

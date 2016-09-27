package com.eniacdevelopment.Application;

import com.eniacdevelopment.Serial.*;
import com.eniacdevelopment.SocketListener;
import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        PacketListenerSubject packetListenerSubject = new PacketListenerSubject();

        SocketPacketListenerObserver socketPacketListenerObserver = new SocketPacketListenerObserver();
        try {
            new SocketListener(socketPacketListenerObserver::addSocket, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        packetListenerSubject.addObserver(socketPacketListenerObserver);

        InitializeSerial(packetListenerSubject);

        while (true) {
        }

        //port.closePort();
        //System.out.println("END!");
    }

    private static void InitializeSerial(PacketListenerSubject packetListenerSubject) {
        SerialPort port = SerialPort.getCommPorts()[0];
        port.addDataListener(packetListenerSubject);
        port.setComPortParameters(9600, 8, 1, 0); //9600baud 8data 1stop 0parity
        port.openPort();
    }
}

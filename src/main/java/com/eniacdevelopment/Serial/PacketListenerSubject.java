package com.eniacdevelopment.Serial;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by larsg on 9/16/2016.
 * A DataListener that can be plugged into JSerialComm.SerialPort for packet handling.
 */
public class PacketListenerSubject implements SerialPortPacketListener {
    private final List<PacketListenerObserver> packetListenerObservers =
            Collections.synchronizedList(new ArrayList<PacketListenerObserver>());

    public void addObserver(PacketListenerObserver packetListenerObserver) {
        synchronized (this.packetListenerObservers) {
            this.packetListenerObservers.add(packetListenerObserver);
        }
    }

    @Override
    public int getPacketSize() {
        return 13;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        byte[] packet = serialPortEvent.getReceivedData();
        System.out.println(new String(packet));

        // Create SerialNotification for all observers
        SerialNotification notification = new SerialNotification();
        notification.Id = Byte.toString(packet[0]);

        synchronized (this.packetListenerObservers) {
            for (PacketListenerObserver observer : this.packetListenerObservers) {
                observer.eventNotify(notification);
            }
        }
    }
}

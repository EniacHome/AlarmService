package com.eniacdevelopment.EniacHome.Serial;

import com.eniacdevelopment.EniacHome.DataModel.Configuration.SerialConfiguration;
import com.eniacdevelopment.EniacHome.Repositories.ConfigurationRepository;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by larsg on 9/16/2016.
 * A DataListener that can be plugged into JSerialComm.SerialPort for packet handling.
 */
public class SerialSubject implements SerialPortPacketListener {
    private final List<PacketListenerObserver> packetListenerObservers =
            Collections.synchronizedList(new ArrayList<PacketListenerObserver>());

    private final ConfigurationRepository configurationRepository;
    private SerialPort serialPortInstance;

    @Inject
    public SerialSubject(ConfigurationRepository configurationRepository){
        this.configurationRepository = configurationRepository;
        this.initializeSerial();
    }

    public void addObserver(PacketListenerObserver packetListenerObserver) {
        synchronized (this.packetListenerObservers) {
            this.packetListenerObservers.add(packetListenerObserver);
        }
    }

    public void initializeSerial(){
        SerialConfiguration serialConfiguration = this.configurationRepository.getActiveConfiguration(SerialConfiguration.class);
        //Default Configuration
        if(serialConfiguration == null) {
            serialConfiguration = new SerialConfiguration() {{
                BaudRate = 9600;
                DataBits = 8;
                Parity = 0;
                StopBits = 1;
                PortDescriptor = "COM1";
            }};
        }

        if(serialPortInstance != null) {
            serialPortInstance.closePort();
        }
        serialPortInstance = SerialPort.getCommPort(serialConfiguration.PortDescriptor);
        serialPortInstance.addDataListener(this);
        serialPortInstance.setComPortParameters(
                serialConfiguration.BaudRate,
                serialConfiguration.DataBits,
                serialConfiguration.StopBits,
                serialConfiguration.Parity);
        serialPortInstance.openPort();
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

package com.eniacdevelopment.EniacHome.Serial;

import com.eniacdevelopment.EniacHome.DataModel.Configuration.SerialConfiguration;
import com.eniacdevelopment.EniacHome.Repositories.Shared.ConfigurationRepository;
import com.eniacdevelopment.EniacHome.Serial.Objects.SerialNotification;
import com.eniacdevelopment.EniacHome.Serial.PacketListenerObservers.PacketListenerObserver;
import com.eniacdevelopment.EniacHome.Serial.PacketParsers.PacketParser;
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

    private final ConfigurationRepository<SerialConfiguration> configurationRepository;
    private final PacketParser packetParser;
    private SerialPort serialPortInstance;

    @Inject
    public SerialSubject(ConfigurationRepository<SerialConfiguration> configurationRepository, PacketParser packetParser){
        this.configurationRepository = configurationRepository;
        this.packetParser = packetParser;
    }

    public void addObserver(PacketListenerObserver packetListenerObserver) {
        synchronized (this.packetListenerObservers) {
            this.packetListenerObservers.add(packetListenerObserver);
        }
    }

    public void initializeSerial(){
        SerialConfiguration serialConfiguration = this.configurationRepository.getActiveConfiguration();
        //Default Configuration
        if(serialConfiguration == null) {
            serialConfiguration = new SerialConfiguration() {{
                BaudRate = 9600;
                DataBits = 8;
                Parity = 0;
                StopBits = 1;
                PortDescriptor = "COM3";
            }};
        }

        if(this.serialPortInstance != null) {
            this.serialPortInstance.closePort();
        }
        this.serialPortInstance = SerialPort.getCommPort(serialConfiguration.PortDescriptor);
        this.serialPortInstance.addDataListener(this);
        this.serialPortInstance.setComPortParameters(
                serialConfiguration.BaudRate,
                serialConfiguration.DataBits,
                serialConfiguration.StopBits,
                serialConfiguration.Parity);
        this.serialPortInstance.openPort();
    }

    @Override
    public int getPacketSize() {
        return 2;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        byte[] packet = serialPortEvent.getReceivedData();

        // Create SerialNotification for all observers
        SerialNotification notification = this.packetParser.parse(packet, serialPortEvent);

        synchronized (this.packetListenerObservers) {
            for (PacketListenerObserver observer : this.packetListenerObservers) {
                observer.eventNotify(notification);
            }
        }
    }
}

package com.eniacdevelopment.EniacHome.Serial;

import com.eniacdevelopment.EniacHome.DataModel.Configuration.SerialConfiguration;
import com.eniacdevelopment.EniacHome.Repositories.ConfigurationRepository;
import com.fazecast.jSerialComm.SerialPort;

/**
 * Created by larsg on 11/13/2016.
 */
public class SerialFactory {
    private SerialPort serialPortInstance;
    private final ConfigurationRepository configurationRepository;

    public SerialFactory(ConfigurationRepository configurationRepository){
        this.configurationRepository = configurationRepository;
    }

    public void InitializeSerial(){
        SerialConfiguration serialConfiguration = null;
        serialConfiguration = configurationRepository.getConfiguration(SerialConfiguration.class, "0");

        if(serialPortInstance != null) {
            serialPortInstance.closePort();
        }
        serialPortInstance = SerialPort.getCommPort(serialConfiguration.PortDescriptor);
//        serialPortInstance.addDataListener(packetListenerSubject);
        serialPortInstance.setComPortParameters(
                serialConfiguration.BaudRate,
                serialConfiguration.DataBits,
                serialConfiguration.StopBits,
                serialConfiguration.Parity);
        serialPortInstance.openPort();
    }
}

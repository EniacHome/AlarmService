package com.eniacdevelopment.EniacHome.Serial;

/**
 * Created by larsg on 11/13/2016.
 */
//public class SerialFactory {
//    private SerialConfiguration serialConfiguration;
//    private SerialPort serialPortInstance;
//
//    public SerialFactory(SerialConfiguration serialConfiguration){
//
//    }
//
//    public void InitializeSerial(){
//        if(serialPortInstance != null) {
//            serialPortInstance.closePort();
//        }
//        serialPortInstance = SerialPort.getCommPort(serialConfigurationq.PortDescriptor);
//        serialPortInstance.addDataListener(packetListenerSubject);
//        serialPortInstance.setComPortParameters(
//                serialConfiguration.BaudRate,
//                serialConfiguration.DataBits,
//                serialConfiguration.StopBits,
//                serialConfiguration.Parity); //9600baud 8data 1stop 0parity
//        serialPortInstance.openPort();
//    }
//}

package com.eniacdevelopment.Application;

import com.eniacdevelopment.Serial.ESPacketListenerObserver;
import com.eniacdevelopment.Serial.PacketListenerSubject;
import com.eniacdevelopment.Serial.SocketPacketListenerObserver;
import com.eniacdevelopment.SocketListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fazecast.jSerialComm.SerialPort;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) {
        //TODO Jackson init should move to IoC eventually
        ObjectMapper objectMapper = new ObjectMapper();

        //TODO ElasticSearch init should move to IoC eventually
        TransportClient transportClient = null;

        try {
            transportClient = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        PacketListenerSubject packetListenerSubject = new PacketListenerSubject();

        SocketPacketListenerObserver socketPacketListenerObserver = new SocketPacketListenerObserver(objectMapper.writer());
        ESPacketListenerObserver esPacketListenerObserver = new ESPacketListenerObserver(transportClient, objectMapper.writer());

        packetListenerSubject.addObserver(socketPacketListenerObserver);
        packetListenerSubject.addObserver(esPacketListenerObserver);

        try {
            new SocketListener(socketPacketListenerObserver::addSocket, 9090, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

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

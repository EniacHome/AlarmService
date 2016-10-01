package com.eniacdevelopment.Application;

import com.eniacdevelopment.Serial.SerialNotification;
import com.eniacdevelopment.Serial.SocketPacketListenerObserver;
import com.eniacdevelopment.SocketListener;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by larsg on 9/27/2016.
 */
public class MainJsonTest {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        SocketPacketListenerObserver socketPacketListenerObserver = new SocketPacketListenerObserver(objectMapper.writer());
        SocketListener socketListener;
        try {
            socketListener = new SocketListener(socketPacketListenerObserver::addSocket, true);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        SerialNotification notification = new SerialNotification(){{
            Id = "hello";
        }};

        System.out.println(String.format("Started listening on port %1$d." +
                " Press enter to send a notification to all connected observers. Press 'q' to quit.",
                socketListener.getPort()));

        while (true)
        {
            String s = br.readLine();
            if(s.equals("q")){
                break;
            }
            socketPacketListenerObserver.eventNotify(notification);
        }

        socketListener.stopListening();
    }
}
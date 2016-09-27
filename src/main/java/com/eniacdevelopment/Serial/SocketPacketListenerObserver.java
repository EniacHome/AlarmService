package com.eniacdevelopment.Serial;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by larsg on 9/18/2016.
 */
public class SocketPacketListenerObserver extends PacketListenerObserver {
    private final List<Socket> sockets = Collections.synchronizedList(new ArrayList<Socket>()); // Thread-Safe list

    public void addSocket(Socket socket) {
        synchronized (this.sockets) {
            this.sockets.add(socket);
        }
    }

    @Override
    public void eventNotify(SerialNotification serialNotification) {
        // Grab all data not covered by SerialNotification from es database

        ObjectMapper mapper = new ObjectMapper();
        String jsonSerialNotification;
        try {
            jsonSerialNotification = mapper.writeValueAsString(serialNotification);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        synchronized (this.sockets) {
            Iterator<Socket> socketIterator = this.sockets.iterator();
            // Iterate all using iterator for thread-safeness
            while (socketIterator.hasNext()) {
                Socket socket = socketIterator.next();
                OutputStreamWriter outputStreamWriter = null;
                try {
                    outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
                    outputStreamWriter.write(jsonSerialNotification);
                    outputStreamWriter.flush();
                } catch (IOException e) {
                    // If unable to cummunicate, force reconnect from client side
                    try {
                        if (outputStreamWriter != null) {
                            outputStreamWriter.close();
                        }
                        socket.close();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    socketIterator.remove();
                }
            }
        }
    }
}

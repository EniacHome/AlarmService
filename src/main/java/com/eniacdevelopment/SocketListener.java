package com.eniacdevelopment;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * Created by larsg on 9/23/2016.
 */
public class SocketListener {
    private static final int PORT = 0;

    private Consumer<Socket> consumer;
    private boolean listen = true;
    private final ServerSocket serverSocket = new ServerSocket();

    private final Thread listenerThread = new Thread(() -> {
        while (listen) try {
            this.consumer.accept(this.serverSocket.accept());
        } catch (IOException ignored) {
        }
    });

    public SocketListener(Consumer<Socket> consumer, boolean startListening) throws IOException {
        this.consumer = consumer;

        if (startListening) {
            this.startListening();
        }
    }

    public int getPort() {
        return this.serverSocket.getLocalPort();
    }

    public void startListening() throws IOException {
        this.serverSocket.bind(new InetSocketAddress(PORT));
        this.listen = true;

        if (!this.listenerThread.isAlive()) {
            this.listenerThread.start();
        }
    }

    public void stopListening() throws IOException {
        this.listen = false;
        this.serverSocket.close();
    }
}
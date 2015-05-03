package com.thoughtworks;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class Client {
    private final InetSocketAddress socketAddress;
    private final UDPServer server;

    public Client(InetSocketAddress socketAddress, UDPServer server) {
        this.socketAddress = socketAddress;
        this.server = server;
    }

    public int port() {
        return socketAddress.getPort();
    }

    public InetAddress address() {
        return socketAddress.getAddress();
    }

    public void send(String message) throws IOException {
        server.send(message, address(), port());
    }

}

package com.thoughtworks;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class UDPServer {

    private DatagramSocket socket;
    private final PushDaemon app;

    public UDPServer(PushDaemon app) {
        this.app = app;
    }

    public void listen(int port) throws IOException {
        socket = new DatagramSocket(port);
        while (true) {
            DatagramPacket received = receive();
            String message = messageOf(received);
            Client client = clientOf(received);
            app.call(message, client);
        }
    }


    public DatagramPacket receive() throws IOException {
        byte[] buf = new byte[4096];
        DatagramPacket received = new DatagramPacket(buf, buf.length);
        socket.receive(received);
        return received;
    }

    public void send(String message, InetAddress address, int port) throws IOException {
        byte[] sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
        socket.send(sendPacket);
    }

    private String messageOf(DatagramPacket received) {
        return new String(received.getData());
    }

    private Client clientOf(DatagramPacket received) {
        return new Client((InetSocketAddress) received.getSocketAddress(), this);
    }
}

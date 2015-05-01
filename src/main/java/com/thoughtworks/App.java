package com.thoughtworks;

import org.apache.http.impl.client.HttpClients;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.sleep;

public class App {


    public static void main(String[] args) throws SocketException {

        Queue<String> queue = new LinkedBlockingQueue<>();
        DatagramSocket socket = new DatagramSocket(6889);

        Pusher pusher = new Pusher(queue, HttpClients.createDefault());
        UDPServer udpServer = new UDPServer(queue, socket);


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    pusher.run();
                }
            }
        });
        t.start();

        while (true) {
            udpServer.run();
        }
    }

}
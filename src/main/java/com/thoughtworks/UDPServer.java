package com.thoughtworks;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class UDPServer {
    private Queue<String> queue;
    private DatagramSocket socket;

    public UDPServer(Queue<String> queue, DatagramSocket socket) {
        this.queue = queue;
        this.socket = socket;
    }

    public void run() {
        try {
            byte[] buf = new byte[4096];

            // receive request
            DatagramPacket received = new DatagramPacket(buf, buf.length);

            socket.receive(received);

            String data = new String(received.getData());
            String command = data.split("\\s")[0];


            if ("PING".equals(command)) {
                byte[] sendData = "PONG".getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, received.getAddress(), received.getPort());
                socket.send(sendPacket);
            } else if ("SEND".equals(command)) {
                String message = data.replace(command, "").trim();
                Pattern p = Pattern.compile("([a-zA-Z0-9_-]*) \"([^\"]*)\"");
                Matcher matcher = p.matcher(message);

                if (matcher.matches()) {
                    String id = matcher.group(1);
                    String alert = matcher.group(2);

                    String json = "{\"registration_ids\" : \"" + id + "\", \"data\" : { \"alert\" : \"" + alert + "\"}}";
                    System.out.println(json);
                    queue.add(json);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

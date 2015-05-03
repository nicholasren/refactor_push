package com.thoughtworks.jobs;

import com.thoughtworks.Client;

import java.io.IOException;

public class Ping implements Job {
    private final Client client;


    public Ping(Client client) {
        this.client = client;
    }

    public void run() throws IOException {
        client.send("PONG");
    }
}

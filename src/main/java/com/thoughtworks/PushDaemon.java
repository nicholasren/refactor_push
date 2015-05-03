package com.thoughtworks;

import com.thoughtworks.jobs.Job;

import java.io.IOException;
import java.net.SocketException;

public class PushDaemon {


    private final Worker worker;
    private final UDPServer server;

    public PushDaemon() throws SocketException {
        worker = new Worker();
        server = new UDPServer(this);

    }

    public void start() throws IOException {
        worker.spawn();
        server.listen(6889);
    }

    public void call(String message, Client client) {
        Request request = new Request(message);
        Job job = Job.create(client, request);
        job.enqueueTo(worker);
    }

}

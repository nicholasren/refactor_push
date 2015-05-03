package com.thoughtworks.jobs;

import com.thoughtworks.Client;
import com.thoughtworks.Request;
import com.thoughtworks.Worker;

import java.io.IOException;

public interface Job {
    void run() throws IOException;

    public static Job create(Client client, Request request) {

        Job job = null;
        if (request.isAcceptable()) {
            job = new NullJob();
        }
        if ("PING".equals(request.command())) {
            job = new Ping(client);
        } else if ("SEND".equals(request.command())) {
            job = new Send(request);
        }
        return job;
    }

    default void enqueueTo(Worker worker) {
        worker.accept(this);
    }
}

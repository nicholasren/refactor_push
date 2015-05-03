package com.thoughtworks.jobs;

import com.thoughtworks.Worker;

import java.io.IOException;

public class NullJob implements Job {
    @Override
    public void run() throws IOException {

    }

    @Override
    public void enqueueTo(Worker worker) {

    }
}

package com.thoughtworks;

import com.thoughtworks.jobs.Job;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Worker {
    private final Queue<Job> queue;

    public Worker() {
        queue = new LinkedBlockingQueue<>();
    }

    public void accept(Job json) {
        this.queue.add(json);
    }

    void spawn() {
        Runnable client = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Job job = queue.poll();
                    if (job != null) {
                        try {
                            job.run();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        Thread t = new Thread(client);
        t.start();
    }
}

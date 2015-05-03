package com.thoughtworks.jobs;

import com.thoughtworks.PushNotification;
import com.thoughtworks.Request;

import java.io.IOException;

public class Send implements Job {

    private final Request request;

    public Send(Request request) {
        this.request = request;
    }

    @Override
    public void run() throws IOException {
        new PushNotification(registrationId(), alert()).deliver();
    }

    private String registrationId() {
        return request.parameters().get(0);
    }

    private String alert() {
        return request.parameters().get(1);
    }


}

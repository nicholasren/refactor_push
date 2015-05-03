package com.thoughtworks;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class PushNotification {

    private static final CloseableHttpClient httpclient = HttpClients.createDefault();
    private String registrationId;
    private String alert;

    public PushNotification(String registrationId, String alert) {
        this.registrationId = registrationId;
        this.alert = alert;
    }

    public void deliver() throws IOException {
        HttpPost post = new HttpPost("https://android.googleapis.com/gcm/send");
        post.setHeader("Authorization", "key=AIzaSyCABSTd47XeIH");
        post.setHeader("Content-Type", "application/json");

        HttpEntity entity = new StringEntity(toJson());
        post.setEntity(entity);

        httpclient.execute(post);
    }

    private String toJson() {
        return "{\"registration_ids\" : \"" + registrationId + "\", \"data\" : { \"alert\" : \"" + alert + "\"}}";
    }
}

package com.thoughtworks;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PusherTest {

    private Queue<String> queue = new LinkedList();
    private Pusher pusher;
    @Mock
    private HttpClient client;

    @Before
    public void before() {
        pusher = new Pusher(queue, client);
    }


    @Test
    public void invoke_client_to_push() throws IOException {
        queue.add("message");
        pusher.run();
        verify(client).execute(any(HttpPost.class));
    }
}

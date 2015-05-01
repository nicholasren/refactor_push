package com.thoughtworks;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.Queue;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class UDPServerTest {

    private UDPServer server;
    private Queue<String> queue = new LinkedList<>();

    @Mock
    private DatagramSocket socket;

    @Before
    public void before() {
        server = new UDPServer(queue, socket);
    }

    @Test
    public void respond_to_heartbeat() throws Exception {

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                prepareReceivedMessage(invocation, "PING");
                return null;
            }
        }).when(socket).receive(any(DatagramPacket.class));

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                String msg = extractSentMessage(invocation);
                assertThat(msg, is("PONG"));
                return null;
            }
        }).when(socket).send(any(DatagramPacket.class));

        server.run();

    }


    @Test
    public void enqueue_message_to_be_send() throws Exception {
        String device_id = "xiaojun";
        String message = "\"helle world\"";

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                prepareReceivedMessage(invocation, "SEND " + device_id + " " + message);
                return null;
            }
        }).when(socket).receive(any(DatagramPacket.class));

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                String msg = extractSentMessage(invocation);
                assertThat(msg, is("{\"registration_ids\" : \" " + device_id + "\", \"data\" : { \"alert\" : " + message + "}}"));
                return null;
            }
        }).when(socket).send(any(DatagramPacket.class));

        server.run();

    }

    private void prepareReceivedMessage(InvocationOnMock invocation, String receivedMessage) {
        Object[] args = invocation.getArguments();
        DatagramPacket received = (DatagramPacket) args[0];
        received.setData(receivedMessage.getBytes());
        received.setAddress(mock(InetAddress.class));
        received.setPort(9000);
    }


    private String extractSentMessage(InvocationOnMock invocation) {
        Object[] args = invocation.getArguments();
        DatagramPacket sent = (DatagramPacket) args[0];
        return new String(sent.getData());
    }
}

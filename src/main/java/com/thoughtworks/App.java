package com.thoughtworks;

import java.io.IOException;

public class App {


    public static void main(String[] args) throws IOException {
        PushDaemon pushDaemon = new PushDaemon();
        pushDaemon.start();
    }
}
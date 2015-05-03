package com.thoughtworks;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {

    private static Pattern PATTERN = Pattern.compile("(PING|SEND)\\s?([a-zA-Z0-9_-]*)?\\s?('[^\']*')?");
    private final boolean acceptable;
    private final Matcher matcher;


    public Request(String message) {
        matcher = PATTERN.matcher(message.trim());
        acceptable = matcher.matches();
    }

    public String command() {
        return matcher.group(1);
    }

    public List<String> parameters() {
        List<String> parameters = new ArrayList<>();

        for (int i = 2; i <= matcher.groupCount(); i++) {
            parameters.add(matcher.group(i));
        }

        return parameters;
    }

    public boolean isAcceptable() {
        return acceptable;
    }
}

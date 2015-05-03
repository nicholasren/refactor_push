package com.thoughtworks;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RequestTest {

    @Test
    public void return_command_when_no_parameter_supplied() {
        Request request = new Request("PING");
        assertThat(request.command(), is("PING"));
    }

    @Test
    public void return_command_when_parameter_supplied() {
        Request request = new Request("SEND xiaojun");
        assertThat(request.command(), is("SEND"));
        assertThat(request.parameters().get(0), is("xiaojun"));
    }

    @Test
    public void return_command_when_multiple_parameters_supplied() {
        Request request = new Request("SEND xiaojun 'hello world'");
        assertThat(request.command(), is("SEND"));
        assertThat(request.parameters().get(0), is("xiaojun"));
        assertThat(request.parameters().get(1), is("'hello world'"));
    }


}

package com.dhemery.victor.frank;

import com.dhemery.network.SerializingEndpoint;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class FranklyFrankDump {
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock public SerializingEndpoint endpoint;
    private Frank frank;

    @Before
    public void setup() {
        frank = new FranklyFrank(endpoint);
    }

    @Test
    public void sendsDumpRequestViaGet() {
        context.checking(new Expectations(){{
            oneOf(endpoint).get(with("/dump"), with(any(Class.class)));
            will(returnValue("some string"));
        }});

        frank.dump();
    }

    @Test
    public void requestsStringResponseType() {
        context.checking(new Expectations(){{
            oneOf(endpoint).get(with(any(String.class)), with(String.class));
            will(returnValue("some string"));
        }});

        frank.dump();
    }

    @Test
    public void returnsEndpointResponseBody() {
        final String endpointResponseBody = "dumped";
        context.checking(new Expectations(){{
            oneOf(endpoint).get("/dump", String.class);
            will(returnValue(endpointResponseBody));
        }});

        String frankResponse = frank.dump();

        assertThat(frankResponse, is(equalTo(endpointResponseBody)));
    }
}

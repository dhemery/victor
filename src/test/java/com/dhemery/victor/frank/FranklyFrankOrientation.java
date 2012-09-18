package com.dhemery.victor.frank;

import com.dhemery.network.SerializingEndpoint;
import com.dhemery.victor.frank.frankly.OrientationResponse;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FranklyFrankOrientation {
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock public SerializingEndpoint endpoint;
    private Frank frank;

    @Before
    public void setup() {
        frank = new FranklyFrank(endpoint);
    }

    @Test
    public void sendsOrientationRequestViaGet() {
        context.checking(new Expectations() {{
            oneOf(endpoint).get(with("/orientation"), with(any(Class.class)));
            will(returnValue(new OrientationResponse("landscape")));
        }});

        frank.orientation();
    }

    @Test
    public void requestsOrientationResponseType() {
        context.checking(new Expectations() {{
            oneOf(endpoint).get(with("/orientation"), with(OrientationResponse.class));
            will(returnValue(new OrientationResponse("landscape")));
        }});

        frank.orientation();
    }

    @Test
    public void reportsLandscapeResponse() {
        context.checking(new Expectations() {{
            allowing(endpoint).get(with("/orientation"), with(OrientationResponse.class));
            will(returnValue(new OrientationResponse("landscape")));
        }});

        assertThat(frank.orientation(), is("landscape"));
    }

    @Test
    public void reportsPortraitResponse() {
        context.checking(new Expectations() {{
            allowing(endpoint).get(with("/orientation"), with(OrientationResponse.class));
            will(returnValue(new OrientationResponse("portrait")));
        }});

        assertThat(frank.orientation(), is("portrait"));
    }
}

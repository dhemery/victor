package com.victor.frank.tests;

import com.dhemery.network.SerializingEndpoint;
import com.dhemery.victor.frank.Frank;
import com.dhemery.victor.frank.FranklyFrank;
import com.dhemery.victor.frank.frankly.AccessibilityCheckResponse;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FranklyFrankAccessibilityCheckTest {
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock public SerializingEndpoint endpoint;
    private Frank frank;

    @Before
    public void setup() {
        frank = new FranklyFrank(endpoint);
    }

    @Test
    public void sendsAccessibilityCheckRequestViaPut() {
        context.checking(new Expectations() {{
            oneOf(endpoint).get(with("/accessibility_check"), with(any(Class.class)));
                will(returnValue(new AccessibilityCheckResponse(true)));
        }});

        frank.accessibilityCheck();
    }

    @Test
    public void requestsAccessibilityCheckResponseType() {
        context.checking(new Expectations() {{
            oneOf(endpoint).get(with(any(String.class)), with(AccessibilityCheckResponse.class));
                will(returnValue(new AccessibilityCheckResponse(true)));
        }});

        frank.accessibilityCheck();
    }

    @Test
    public void returnsFalseIfAccessibilityCheckResponseIsFalse() {
        context.checking(new Expectations(){{
            allowing(endpoint).get("/accessibility_check", AccessibilityCheckResponse.class);
                will(returnValue(new AccessibilityCheckResponse(false)));
        }});

        assertThat(frank.accessibilityCheck(), is(false));
    }

    @Test
    public void returnsTrueIfAccessibilityCheckResponseIsTrue() {
        context.checking(new Expectations(){{
            allowing(endpoint).get("/accessibility_check", AccessibilityCheckResponse.class);
                will(returnValue(new AccessibilityCheckResponse(true)));
        }});

        assertThat(frank.accessibilityCheck(), is(true));
    }
}

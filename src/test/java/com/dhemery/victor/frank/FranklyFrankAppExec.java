package com.dhemery.victor.frank;

import com.dhemery.network.SerializingEndpoint;
import com.dhemery.victor.frank.frankly.AppExecOperation;
import com.dhemery.victor.frank.frankly.MessageResponse;
import com.dhemery.victor.frank.frankly.Operation;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class FranklyFrankAppExec {
    public static final String IGNORED_METHOD_NAME = null;
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock public SerializingEndpoint endpoint;
    private Frank frank;

    @Before
    public void setup() {
        frank = new FranklyFrank(endpoint);
    }

    @Test
    public void sendsAppExecOperationViaPut() {
        context.checking(new Expectations(){{
            oneOf(endpoint).put(with("/app_exec"), with(any(AppExecOperation.class)), with(any(Class.class)));
                will(returnValue(anAppExecResponse()));
        }});

        frank.appExec(IGNORED_METHOD_NAME);
    }

    @Test
    public void requestsMessageResponseReturnType() {
        context.checking(new Expectations(){{
            allowing(endpoint).put(with("/app_exec"), with(any(AppExecOperation.class)), with(MessageResponse.class));
            will(returnValue(anAppExecResponse()));
        }});

        frank.appExec(IGNORED_METHOD_NAME);
    }

    @Test
    public void appExecOperationIncludesMethodName() {
        final String methodName = "theMethodName";
        context.checking(new Expectations(){{
            oneOf(endpoint).put(with("/app_exec"), with(appExecOperation(methodName)), with(any(Class.class)));
                will(returnValue(anAppExecResponse()));
        }});

        frank.appExec(methodName);
    }

    @Test
    public void appExecOperationIncludesArguments() {
        String methodName = "theMethodName";
        String argument1 = "argument 1";
        String argument2 = "argument 2";
        String argument3 = "argument 3";
        final AppExecOperation expectedOperation = appExecOperation(methodName, argument1, argument2, argument3);

        context.checking(new Expectations(){{
            oneOf(endpoint).put(with("/app_exec"), with(expectedOperation), with(any(Class.class)));
                will(returnValue(anAppExecResponse()));
        }});

        frank.appExec(methodName, argument1, argument2, argument3);
    }

    @Test
    public void appExecReturnsFirstResultIfRequestSucceeds() {
        final String methodName = "ignoredMethodName";
        final String theFirstReturnedResult = "the first result";
        context.checking(new Expectations(){{
            allowing(endpoint).put("/app_exec", appExecOperation(methodName), MessageResponse.class);
                will(returnValue(aMessageResponseWith(theFirstReturnedResult)));
        }});

        String frankResult = frank.appExec(methodName);
        assertThat(frankResult, is(equalTo(theFirstReturnedResult)));
    }

    @Test(expected = IosOperationException.class)
    public void appExecThrowsIfRequestFails() {
        final String methodName = "ignoredMethodName";
        context.checking(new Expectations() {{
            allowing(endpoint).put("/app_exec", appExecOperation(methodName), MessageResponse.class);
                will(returnValue(aMessageFailureResponse()));
        }});

        frank.appExec(methodName);
    }

    private static AppExecOperation appExecOperation(String methodName, Object... parameters) {
        return new AppExecOperation(new Operation(methodName, parameters));
    }

    private static MessageResponse anAppExecResponse() {
        return aMessageResponseWith("some result");
    }

    private static MessageResponse aMessageResponseWith(String... result) {
        return new MessageResponse(true, Arrays.asList(result), null, null);
    }

    private static MessageResponse aMessageFailureResponse() {
        return new MessageResponse(false, Collections.<String>emptyList(), "ignored failure reason", "ignored failure details");
    }
}

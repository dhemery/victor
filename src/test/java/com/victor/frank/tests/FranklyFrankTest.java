package com.victor.frank.tests;

import com.dhemery.network.SerializingEndpoint;
import com.dhemery.victor.frank.Frank;
import com.dhemery.victor.frank.FranklyFrank;
import com.dhemery.victor.frank.IosOperationException;
import com.dhemery.victor.frank.frankly.*;
import com.victor.fixtures.MapOperationBuilder;
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

public class FranklyFrankTest {
    public static final String IGNORED_ENGINE = null;
    public static final String IGNORED_METHOD_NAME = null;
    public static final String IGNORED_QUERY = null;
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock public SerializingEndpoint endpoint;
    private Frank frank;

    @Before
    public void setup() {
        frank = new FranklyFrank(endpoint);
    }

    @Test
    public void accessibilityCheckCallsEndpointAccessibilityCheck() {
        context.checking(new Expectations() {{
            oneOf(endpoint).get("/accessibility_check", AccessibilityCheckResponse.class);
                will(returnValue(new AccessibilityCheckResponse(true)));
        }});

        frank.accessibilityCheck();
    }

    @Test
    public void accessibilityCheckReturnsFalseIfEndpointResponseIsFalse() {
        context.checking(new Expectations(){{
            oneOf(endpoint).get("/accessibility_check", AccessibilityCheckResponse.class);
                will(returnValue(new AccessibilityCheckResponse(false)));
        }});

        assertThat(frank.accessibilityCheck(), is(false));
    }

    @Test
    public void accessibilityCheckReturnsTrueIfEndpointResponseIsTrue() {
        context.checking(new Expectations(){{
            oneOf(endpoint).get("/accessibility_check", AccessibilityCheckResponse.class);
            will(returnValue(new AccessibilityCheckResponse(true)));
        }});

        assertThat(frank.accessibilityCheck(), is(true));
    }


    @Test
    public void appExecCallsEndpointPutAppExec() {
        context.checking(new Expectations(){{
            oneOf(endpoint).put(with("/app_exec"), with(any(AppExecOperation.class)), with(any(Class.class)));
            will(returnValue(anAppExecResponse()));
        }});

        frank.appExec(IGNORED_METHOD_NAME);
    }

    @Test
    public void appExecEndpointRequestIncludesMethodName() {
        final String methodName = "theMethodName";
        context.checking(new Expectations(){{
            oneOf(endpoint).put(with("/app_exec"), with(appExecOperation(methodName)), with(any(Class.class)));
            will(returnValue(anAppExecResponse()));
        }});

        frank.appExec(methodName);
    }

    @Test
    public void appExecEndpointRequestIncludesParameters() {
        String methodName = "theMethodName";
        String parameter1 = "parameter 1";
        String parameter2 = "parameter 2";
        String parameter3 = "parameter 3";
        final AppExecOperation expectedOperation = appExecOperation(methodName, parameter1, parameter2, parameter3);

        context.checking(new Expectations(){{
            oneOf(endpoint).put(with("/app_exec"), with(expectedOperation), with(any(Class.class)));
            will(returnValue(anAppExecResponse()));
        }});

        frank.appExec(methodName, parameter1, parameter2, parameter3);
    }

    @Test
    public void appExecEndpointRequestSpecifiesMessageResponseReturnType() {
        final String methodName = "ignoredMethodName";
        context.checking(new Expectations(){{
            allowing(endpoint).put(with("/app_exec"), with(any(AppExecOperation.class)), with(MessageResponse.class));
            will(returnValue(anAppExecResponse()));
        }});

        frank.appExec(methodName);
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

    @Test
    public void mapCallsEndpointPutMap() {
        context.checking(new Expectations(){{
            oneOf(endpoint).put(with("/map"), with(any(MapOperation.class)), with(any(Class.class)));
                will(returnValue(aMessageResponse()));
        }});

        frank.map(IGNORED_ENGINE, IGNORED_QUERY, IGNORED_METHOD_NAME);
    }

    @Test
    public void mapRequestIncludesEngineName() {
        final String engineName= "the engine name";
        context.checking(new Expectations(){{
            MapOperation mapOperation = mapOperation().engine(engineName).build();
            oneOf(endpoint).put(with("/map"), with(mapOperation), with(any(Class.class)));
                will(returnValue(anAppExecResponse()));
        }});

        frank.map(engineName, IGNORED_QUERY, IGNORED_METHOD_NAME);
    }

//    @Test
//    public void appExecRequestIncludesParameters() {
//        String methodName = "theMethodName";
//        String parameter1 = "parameter 1";
//        String parameter2 = "parameter 2";
//        String parameter3 = "parameter 3";
//        final AppExecOperation expectedOperation = appExecOperation(methodName, parameter1, parameter2, parameter3);
//
//        context.checking(new Expectations(){{
//            oneOf(endpoint).put(with("/app_exec"), with(expectedOperation), with(any(Class.class)));
//                will(returnValue(anAppExecResponse()));
//        }});
//
//        frank.appExec(methodName, parameter1, parameter2, parameter3);
//    }

    @Test
    public void mapEndpointRequestSpecifiesMessageResponseReturnType() {
        context.checking(new Expectations(){{
            allowing(endpoint).put(with("/map"), with(any(MapOperation.class)), with(MessageResponse.class));
                will(returnValue(anAppExecResponse()));
        }});

        frank.map(IGNORED_ENGINE, IGNORED_QUERY, IGNORED_METHOD_NAME);
    }

//    @Test
//    public void appExecReturnsFirstResultIfRequestSucceeds() {
//        final String methodName = "ignoredMethodName";
//        final String theFirstReturnedResult = "the first result";
//        context.checking(new Expectations(){{
//            allowing(endpoint).put("/app_exec", appExecOperation(methodName), MessageResponse.class);
//                will(returnValue(aMessageResponseWith(theFirstReturnedResult)));
//        }});
//
//        String frankResult = frank.appExec(methodName);
//        assertThat(frankResult, is(equalTo(theFirstReturnedResult)));
//    }
//
    @Test(expected = IosOperationException.class)
    public void appExecThrowsIfRequestFails() {
        final String methodName = "ignoredMethodName";
        context.checking(new Expectations() {{
            allowing(endpoint).put("/app_exec", appExecOperation(methodName), MessageResponse.class);
            will(returnValue(aMessageFailureResponse()));
        }});

        frank.appExec(methodName);
    }

    @Test
    public void dumpCallsEndpointDump() {
        context.checking(new Expectations(){{
            oneOf(endpoint).get("/dump", String.class); will(returnValue("ignored"));
        }});

        frank.dump();
    }

    @Test
    public void dumpReturnsEndpointResponse() {
        final String endpointResponseContents = "dumped";
        context.checking(new Expectations(){{
            oneOf(endpoint).get("/dump", String.class); will(returnValue(endpointResponseContents));
        }});

        String frankResponse = frank.dump();

        assertThat(frankResponse, is(equalTo(endpointResponseContents)));
    }

    private AppExecOperation appExecOperation(String methodName, Object...parameters) {
        return new AppExecOperation(new Operation(methodName, parameters));
    }

    private MessageResponse anAppExecResponse() {
        return aMessageResponseWith("some result");
    }

    private MessageResponse aMessageResponse() {
        return new MessageResponse(true, Collections.<String>emptyList(), null, null);
    }

    private MessageResponse aMessageResponseWith(String result) {
        return new MessageResponse(true, Arrays.asList(result), null, null);
    }

    private MessageResponse aMessageFailureResponse() {
        return new MessageResponse(false, Collections.<String>emptyList(), "ignored failure reason", "ignored failure details");
    }

    private MapOperationBuilder mapOperation() {
        return new MapOperationBuilder();
    }
}

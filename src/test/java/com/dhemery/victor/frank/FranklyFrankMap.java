package com.dhemery.victor.frank;

import com.dhemery.network.SerializingEndpoint;
import com.dhemery.victor.frank.frankly.*;
import com.dhemery.victor.fixtures.MapOperationBuilder;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class FranklyFrankMap {
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
    public void sendsMapOperationViaPut() {
        context.checking(new Expectations(){{
            oneOf(endpoint).put(with("/map"), with(any(MapOperation.class)), with(any(Class.class)));
                will(returnValue(aMessageResponse()));
        }});

        frank.map(IGNORED_ENGINE, IGNORED_QUERY, IGNORED_METHOD_NAME);
    }

    @Test
    public void requestsMessageResponseReturnType() {
        context.checking(new Expectations(){{
            allowing(endpoint).put(with(any(String.class)), with(any(MapOperation.class)), with(MessageResponse.class));
            will(returnValue(aMessageResponse()));
        }});

        frank.map(IGNORED_ENGINE, IGNORED_QUERY, IGNORED_METHOD_NAME);
    }

    @Test
    public void mapOperationIncludesEngineName() {
        String engineName= "the engine name";
        final MapOperation mapOperation = mapOperation().engine(engineName).build();
        context.checking(new Expectations(){{
            oneOf(endpoint).put(with("/map"), with(mapOperation), with(any(Class.class)));
                will(returnValue(aMessageResponse()));
        }});

        frank.map(engineName, IGNORED_QUERY, IGNORED_METHOD_NAME);
    }

    @Test
    public void mapOperationIncludesQuery() {
        String query = "the query name";
        final MapOperation mapOperation = mapOperation().query(query).build();
        context.checking(new Expectations(){{
            oneOf(endpoint).put(with("/map"), with(mapOperation), with(any(Class.class)));
                will(returnValue(aMessageResponse()));
        }});

        frank.map(IGNORED_ENGINE, query, IGNORED_METHOD_NAME);
    }

    @Test
    public void mapOperationIncludesMethodName() {
        String methodName = "the method name";
        final MapOperation mapOperation = mapOperation().method(methodName).build();
        context.checking(new Expectations(){{
            oneOf(endpoint).put(with("/map"), with(mapOperation), with(any(Class.class)));
                will(returnValue(aMessageResponse()));
        }});

        frank.map(IGNORED_ENGINE, IGNORED_QUERY, methodName);
    }

    @Test
    public void mapOperationIncludesArguments() {
        String argument1 = "argument 1";
        String argument2 = "argument 2";
        String argument3 = "argument 3";
        final MapOperation mapOperation = mapOperation().arguments(argument1, argument2, argument3).build();

        context.checking(new Expectations(){{
            oneOf(endpoint).put(with("/map"), with(mapOperation), with(any(Class.class)));
                will(returnValue(aMessageResponse()));
        }});

        frank.map(IGNORED_ENGINE, IGNORED_QUERY, IGNORED_METHOD_NAME, argument1, argument2, argument3);
    }

    @Test
    public void mapReturnsResultsIfRequestSucceeds() {
        final String result1 = "result 1";
        final String result2 = "result 2";
        final String result3 = "result 3";
        context.checking(new Expectations(){{
            allowing(endpoint).put(with("/map"), with(any(MapOperation.class)), with(any(Class.class)));
                will(returnValue(aMessageResponseWith(result1, result2, result3)));
        }});

        List<String> frankResult = frank.map(IGNORED_ENGINE, IGNORED_QUERY, IGNORED_METHOD_NAME);
        assertThat(frankResult, containsInAnyOrder(result1, result2, result3));
    }

    @Test(expected = IosOperationException.class)
    public void mapThrowsIfRequestFails() {
        context.checking(new Expectations() {{
            allowing(endpoint).put(with("/map"), with(any(MapOperation.class)), with(any(Class.class)));
                will(returnValue(aMessageFailureResponse()));
        }});

        frank.map(IGNORED_ENGINE, IGNORED_QUERY, IGNORED_METHOD_NAME);
    }

    private static MessageResponse aMessageResponse() {
        return new MessageResponse(true, Collections.<String>emptyList(), null, null);
    }

    private static MessageResponse aMessageResponseWith(String... result) {
        return new MessageResponse(true, Arrays.asList(result), null, null);
    }

    private static MessageResponse aMessageFailureResponse() {
        return new MessageResponse(false, Collections.<String>emptyList(), "ignored failure reason", "ignored failure details");
    }

    private static MapOperationBuilder mapOperation() {
        return new MapOperationBuilder();
    }
}

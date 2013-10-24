package com.dhemery.victor.frank;

import com.dhemery.victor.frank.fixtures.FranklyFrankTest;
import com.dhemery.victor.frank.frankly.MapOperation;
import com.dhemery.victor.frank.frankly.MessageResponse;
import com.dhemery.victor.frank.frankly.Operation;
import org.jmock.Expectations;
import org.junit.Test;

import java.util.List;

import static com.dhemery.victor.frank.fixtures.FranklyMessageBuilder.failureMessageResponse;
import static com.dhemery.victor.frank.fixtures.FranklyMessageBuilder.messageResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class FranklyFrankMap extends FranklyFrankTest {
    @Test
    public void mapReturnsResultsIfRequestSucceeds() {
        String result1 = "result 1";
        String result2 = "result 2";
        String result3 = "result 3";
        given(appRespondsTo(mapRequest("engine", "query", "methodName"), with(messageResponse(result1, result2, result3))));

        List<String> frankResult = frank.map("engine", "query", "methodName");

        assertThat(frankResult, containsInAnyOrder(result1, result2, result3));
    }

    @Test(expected = IosOperationException.class)
    public void mapThrowsIfRequestFails() {
        given(appRespondsTo(mapRequest("engine", "query", "methodName"), with(failureMessageResponse())));

        frank.map("engine", "query", "methodName");
    }

    private Expectations appRespondsTo(MapOperation operation, MessageResponse response) {
        return appRespondsTo("/map", operation, response);
    }

    public static MapOperation mapRequest(String engine, String query, String methodName) {
        return new MapOperation(engine, query, new Operation(methodName));
    }
}

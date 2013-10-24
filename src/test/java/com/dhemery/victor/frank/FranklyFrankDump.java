package com.dhemery.victor.frank;

import com.dhemery.victor.frank.fixtures.FranklyFrankTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FranklyFrankDump extends FranklyFrankTest {
    @Test
    public void returnsEndpointResponseBody() {
        String expectedResponse = "Response to dump";

        given(appRespondsTo(dump(), with(expectedResponse)));

        assertThat(frank.dump(), is(expectedResponse));
    }

    private String dump() {
        return "/dump";
    }
}

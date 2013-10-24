package com.dhemery.victor.frank.fixtures;

import com.dhemery.network.SerializingEndpoint;
import com.dhemery.victor.frank.Frank;
import com.dhemery.victor.frank.FranklyFrank;
import com.dhemery.victor.fixtures.JMockTest;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.junit.Before;

public class FranklyFrankTest extends JMockTest {
    @Mock public SerializingEndpoint endpoint;
    protected Frank frank;

    @Before
    public void setup() {
        frank = new FranklyFrank(endpoint);
    }

    protected <B,R> Expectations appRespondsTo(final String path, final B body, final R response) {
        return new Expectations(){{
            oneOf(endpoint).put(path, body, response.getClass());
            will(returnValue(response));
        }};
    }

    protected <R> Expectations appRespondsTo(final String path, final R response) {
        return new Expectations(){{
            oneOf(endpoint).get(path, response.getClass());
            will(returnValue(response));
        }};
    }

    protected <B> Expectations appReceives(final String path, final B body) {
        return new Expectations(){{
            oneOf(endpoint).put(path, body, Void.class);
        }};
    }

    protected <R> R with(R response) {
        return response;
    }
}

package com.dhemery.publishing;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ADistributingPublisher {
    private DistributingPublisher publisher;
    private MySubscriber subscriber;

    @Before
    public void setUp() {
        publisher = new DistributingPublisher();
        subscriber = new MySubscriber();
    }

    @Test
    @Ignore("wip")
    public void publishesToASubscriber() {
        publisher.subscribe(subscriber);
        publisher.publish(new Object());
        assertThat(subscriber.receiptCount, equalTo(1));
    }

    private class MySubscriber {
        public int receiptCount;

        @Subscribe
        public void myEventHandler(Object publication) {
            receiptCount++;
        }
    }
}

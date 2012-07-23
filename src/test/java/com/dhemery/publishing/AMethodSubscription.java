package com.dhemery.publishing;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AMethodSubscription {
    @Test
    public void deliversAPublication() throws NoSuchMethodException {
        final Counter delivery = new Counter();
        final Object subscriber = new Object(){
            @Subscribe public void receive(Publication1 publication) {
                delivery.record();
            }
        };
        final Publication1 publication = new Publication1(){};

        Subscription subscription = new MethodSubscription(subscriber, subscriber.getClass().getDeclaredMethod("receive", Publication1.class));
        subscription.deliver(publication);

        assertThat(delivery.count, equalTo(1));
    }
}

package com.dhemery.publishing;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ADistributingPublisher {
    private DistributingPublisher publisher;

    @Before
    public void setUp() {
        publisher = new DistributingPublisher();
    }

    @Test
    public void publishesToASubscriber() {
        final Counter delivery = new Counter();
        final Publication1 publication1 = new Publication1();

        final Object subscribesTo1 = new Object(){
            @Subscribe public void subscribe(Publication1 publication) {
                delivery.record();
            }
        };

        publisher.subscribe(subscribesTo1);
        publisher.publish(publication1);

        assertThat(delivery.count, equalTo(1));
    }

    @Test
    public void publishesToAMultiplySubscribedSubscriber() {
        final Publication1 publication1 = new Publication1();
        final Counter deliveryByMethod1 = new Counter();
        final Counter deliveryByMethod2 = new Counter();
        final Counter deliveryOfPublication1 = new Counter();

        final Object subscribesTo1Twice = new Object() {
            @Subscribe public void method1(Publication1 publication) {
                deliveryByMethod1.record();
                deliveryOfPublication1.record();
            }
            @Subscribe public void method2(Publication1 publication) {
                deliveryByMethod2.record();
                deliveryOfPublication1.record();
            }
        };

        publisher.subscribe(subscribesTo1Twice);
        publisher.publish(publication1);

        assertThat(deliveryByMethod1.count, equalTo(1));
        assertThat(deliveryByMethod2.count, equalTo(1));
        assertThat(deliveryOfPublication1.count, equalTo(2));
    }

    @Test
    public void publishesToMultipleSubscribersOfASinglePublication() {
        Publication1 publication1 = new Publication1();
        final Counter deliveryToSubscriber1 = new Counter();
        final Counter deliveryToSubscriber2 = new Counter();
        final Counter deliveryOfPublication1 = new Counter();

        final Object subscriber1 = new Object(){
            @Subscribe public void receive(Publication1 publication) {
                deliveryToSubscriber1.record();
                deliveryOfPublication1.record();
            }
        };

        final Object subscriber2 = new Object(){
            @Subscribe public void receive(Publication1 publication) {
                deliveryToSubscriber2.record();
                deliveryOfPublication1.record();
            }
        };

        publisher.subscribe(subscriber1);
        publisher.subscribe(subscriber2);
        publisher.publish(publication1);

        assertThat(deliveryToSubscriber1.count, equalTo(1));
        assertThat(deliveryToSubscriber2.count, equalTo(1));
        assertThat(deliveryOfPublication1.count, equalTo(2));
    }
}

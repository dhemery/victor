package com.dhemery.publishing;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ASubscriberInspector {
    private final Object subscriberWithNoSubscriptions = new Object(){};
    private final Object subscriberWithOneSubscription = new Object(){
        @Subscribe
        public void subscription(Object o) {}
    };
    private final Object subscriberWithMultipleSubscriptions = new Object(){
        @Subscribe
        public void subscription1(Object o) {}
        @Subscribe
        public void subscription2(Object o) {}
        @Subscribe
        public void subscription3(Object o) {}
        @Subscribe
        public void subscription4(Object o) {}
        @Subscribe
        public void subscription5(Object o) {}
        @Subscribe
        public void subscription6(Object o) {}
    };
    private final Object subscriberWithSubscriptionAndAnotherMethod = new Object(){
        @Subscribe
        public void subscription(Object o) {}
        public void notASubscription(Object o) {}
    };
    private final SubscriberInspector subscriberInspector = new SubscriberInspector();

    @Test
    public void findsNoSubscriptionsMethodsIfSubscriberHasNoSubscription() {
        Set<Method> subscriptions = subscriberInspector.subscriptionsOn(subscriberWithNoSubscriptions);
        assertThat(subscriptions, is(empty()));
    }

    @Test
    public void findsOneSubscriptionIfSubscriberHasOneSubscription() {
        Set<Method> subscriptions = subscriberInspector.subscriptionsOn(subscriberWithOneSubscription);
        Method method = subscriberWithOneSubscription.getClass().getDeclaredMethods()[0];
        assertThat(subscriptions, hasItem(method));
    }

    @Test
    public void findsAllSubscriptions() {
        Set<Method> subscriptions = subscriberInspector.subscriptionsOn(subscriberWithMultipleSubscriptions);
        Method[] allMethods = subscriberWithMultipleSubscriptions.getClass().getDeclaredMethods();
        assertThat(subscriptions, containsInAnyOrder(allMethods));
    }

    @Test
    public void findsOnlySubscriptions() throws NoSuchMethodException {
        Set<Method> subscriptions = subscriberInspector.subscriptionsOn(subscriberWithSubscriptionAndAnotherMethod);
        Method subscription = subscriberWithSubscriptionAndAnotherMethod.getClass().getDeclaredMethod("subscription", Object.class);
        Method notASubscription = subscriberWithSubscriptionAndAnotherMethod.getClass().getDeclaredMethod("notASubscription", Object.class);
        assertThat(subscriptions, hasItem(subscription));
        assertThat(subscriptions, not(hasItem(notASubscription)));
    }
}

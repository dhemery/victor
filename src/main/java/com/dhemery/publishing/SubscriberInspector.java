package com.dhemery.publishing;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubscriberInspector {
    public Set<Method> subscriptionsOn(Object subscriber) {
        SubscriptionMethodFilter filter = new SubscriptionMethodFilter();
        Class<? extends Object> subscriberClass = subscriber.getClass();
        List<Method> methods = Arrays.asList(subscriberClass.getMethods());
        Set<Method> subscriptions = new HashSet<Method>();
        for (Method method : methods) {
            if (filter.accepts(method)) {
                subscriptions.add(method);
            }
        }
        return subscriptions;
    }
}
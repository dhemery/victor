package com.dhemery.publishing;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubscriberInspector {
    public static List<Method> subscriptionsOn(Object subscriber) {
        SubscriptionMethodFilter filter = new SubscriptionMethodFilter();
        List<Method> methods = Arrays.asList(subscriber.getClass().getMethods());
        List<Method> subscriptions = new ArrayList<Method>();
        for (Method method : methods) {
            if (filter.accepts(method)) {
                subscriptions.add(method);
            }
        }
        return subscriptions;
    }
}
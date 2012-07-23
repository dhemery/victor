package com.dhemery.publishing;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodSubscription implements Subscription {
    private final Object subscriber;
    private final Method method;

    public MethodSubscription(Object subscriber, Method method) {
        this.subscriber = subscriber;
        this.method = method;
    }

    @Override
    public void deliver(Object publication) {
        try {
            method.invoke(subscriber, publication);
        } catch (IllegalAccessException ignore) {
        } catch (InvocationTargetException ignore) {
        }
    }
}

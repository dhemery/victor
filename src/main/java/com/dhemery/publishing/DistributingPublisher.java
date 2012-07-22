package com.dhemery.publishing;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DistributingPublisher implements Publisher, Distributor {
    private final Set<Object> subscribers = new HashSet<Object>();

    @Override
    public void subscribe(Object... subscriber) {
        subscribers.addAll(Arrays.asList(subscriber));
    }

    @Override
    public void unsubscribe(Object... subscriber) {
    }

    @Override
    public void publish(Object publication) {
    }
}

package com.dhemery.publishing;

import com.google.common.eventbus.EventBus;

/**
 * A publisher that uses an {@link EventBus} to distribute publications to subscribers.
 */
public class EventBusPublisher implements Publisher<Object>, Distributor<Object> {
    private final EventBus eventBus;

    /**
     * Create a publisher that publishes through the given {@link EventBus}.
     */
    public EventBusPublisher(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void publish(Object publication) {
        eventBus.post(publication);
    }

    @Override
    public void subscribe(Object subscriber) {
        eventBus.register(subscriber);
    }

    @Override
    public void unsubscribe(Object subscriber) {
        eventBus.unregister(subscriber);
    }
}

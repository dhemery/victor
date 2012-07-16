package com.dhemery.victor;

import java.util.HashSet;
import java.util.Set;

/**
 * Listens for OSCommand events and republishes them to its own subscribers.
 */
public class ListeningOSCommandPublisher implements OSCommandPublisher, OSCommandSubscriber {
    private final Set<OSCommandSubscriber> subscribers = new HashSet<OSCommandSubscriber>();

    @Override
    public void subscribe(OSCommandSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void willRun(OSCommand command) {
        for(OSCommandSubscriber subscriber : subscribers) {
            subscriber.willRun(command);
        }
    }

    @Override
    public void started(OSCommand command) {
        for(OSCommandSubscriber subscriber : subscribers) {
            subscriber.started(command);
        }
    }

    @Override
    public void returned(OSCommand command, String output) {
        for(OSCommandSubscriber subscriber : subscribers) {
            subscriber.returned(command, output);
        }
    }

    @Override
    public void unsubscribe(OSCommandSubscriber subscriber) {
        subscribers.remove(subscriber);
    }
}

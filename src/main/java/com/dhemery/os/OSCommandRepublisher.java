package com.dhemery.os;

import java.util.HashSet;
import java.util.Set;

/**
 * Listens for OSCommand events and republishes them to its own subscribers.
 * Just like the neighborhood gossip.
 */
public class OSCommandRepublisher implements OSCommandPublisher, OSCommandSubscriber {
    private final Set<OSCommandSubscriber> subscribers = new HashSet<OSCommandSubscriber>();

    @Override
    public void subscribe(OSCommandSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    /**
     * Notify the republisher's subscribers that a command will run.
     * @param command the command that will run
     */
    @Override
    public void willRun(OSCommand command) {
        for(OSCommandSubscriber subscriber : subscribers) {
            subscriber.willRun(command);
        }
    }

    /**
     * Notify the republisher's subscribers that a command started executing.
     * @param command the command that started executing
     */
    @Override
    public void ran(OSCommand command) {
        for(OSCommandSubscriber subscriber : subscribers) {
            subscriber.ran(command);
        }
    }

    /**
     * Notify the republisher's subscribers of the output written by a command.
     * @param command the command that wrote the output
     * @param output the command's output
     */
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

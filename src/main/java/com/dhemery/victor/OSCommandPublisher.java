package com.dhemery.victor;

import java.util.HashSet;
import java.util.Set;

public class OSCommandPublisher implements OSCommandListener {
    private final Set<OSCommandListener> subscribers = new HashSet<OSCommandListener>();


    public void subscribe(OSCommandListener subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void willRun(OSCommand command) {
        for(OSCommandListener subscriber : subscribers) subscriber.willRun(command);
    }

    @Override
    public void started(OSCommand command, Process process) {
        for(OSCommandListener subscriber : subscribers) subscriber.started(command, process);
    }

    @Override
    public void returned(OSCommand command, String output) {
        for(OSCommandListener subscriber : subscribers) subscriber.returned(command, output);
    }

    public void unsubscribe(OSCommandListener subscriber) {
        subscribers.remove(subscriber);
    }
}

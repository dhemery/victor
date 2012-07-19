package com.dhemery.victor.frank;

import com.dhemery.victor.frankly.AccessibilityCheckResponse;
import com.dhemery.victor.frankly.MessageResponse;
import com.dhemery.victor.frankly.OrientationResponse;

import java.util.HashSet;
import java.util.Set;

public class FrankRepublisher implements FrankPublisher, FrankSubscriber {
    private final Set<FrankSubscriber> subscribers = new HashSet<FrankSubscriber>();

    @Override
    public void accessibilityCheckRequest() {
        for(FrankSubscriber subscriber : subscribers) {
            subscriber.accessibilityCheckRequest();
        }
    }

    @Override
    public void accessibilityCheckResponse(AccessibilityCheckResponse response) {
        for(FrankSubscriber subscriber : subscribers) {
            subscriber.accessibilityCheckResponse(response);
        }
    }

    @Override
    public void appExecRequest(String name, Object[] arguments) {
        for(FrankSubscriber subscriber : subscribers) {
            subscriber.appExecRequest(name, arguments);
        }
    }

    @Override
    public void appExecResponse(String name, Object[] arguments, MessageResponse response) {
        for(FrankSubscriber subscriber : subscribers) {
            subscriber.appExecResponse(name, arguments, response);
        }
    }

    @Override
    public void dumpRequest() {
        for(FrankSubscriber subscriber : subscribers) {
            subscriber.dumpRequest();
        }
    }

    @Override
    public void dumpResponse(String response) {
        for(FrankSubscriber subscriber : subscribers) {
            subscriber.dumpResponse(response);
        }
    }

    @Override
    public void mapRequest(String engine, String query, String name, Object[] arguments) {
        for(FrankSubscriber subscriber : subscribers) {
            subscriber.mapRequest(engine, query, name, arguments);
        }
    }

    @Override
    public void mapResponse(String engine, String query, String name, Object[] arguments, MessageResponse response) {
        for(FrankSubscriber subscriber : subscribers) {
            subscriber.mapResponse(engine, query, name, arguments, response);
        }
    }

    @Override
    public void orientationRequest() {
        for(FrankSubscriber subscriber : subscribers) {
            subscriber.orientationRequest();
        }
    }

    @Override
    public void orientationResponse(OrientationResponse response) {
        for(FrankSubscriber subscriber : subscribers) {
            subscriber.orientationResponse(response);
        }
    }

    @Override
    public void subscribe(FrankSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void typeIntoKeyboardRequest(String text) {
        for(FrankSubscriber subscriber : subscribers) {
            subscriber.typeIntoKeyboardRequest(text);
        }
    }

    @Override
    public void typeIntoKeyboardResponse() {
        for(FrankSubscriber subscriber : subscribers) {
            subscriber.typeIntoKeyboardResponse();
        }
    }

    @Override
    public void unsubscribe(FrankSubscriber subscriber) {
        subscribers.remove(subscriber);
    }
}

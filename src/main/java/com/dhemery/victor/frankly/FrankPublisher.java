package com.dhemery.victor.frankly;

import com.dhemery.victor.frank.FrankSubscriber;

public class FrankPublisher {
    public void accessibilityCheckRequest() {
    }

    public void accessibilityCheckResponse(AccessibilityCheckResponse response) {
    }

    public void appExecRequest(String name, Object[] arguments) {
    }

    public void appExecResponse(String name, Object[] arguments, MessageResponse response) {
    }

    public void dumpRequest() {
    }

    public void dumpResponse(String response) {
    }

    public void mapRequest(String engine, String query, String name, Object[] arguments) {
    }

    public void mapResponse(String engine, String query, String name, Object[] arguments, MessageResponse response) {
    }

    public void orientationRequest() {
    }

    public void orientationResponse(OrientationResponse response) {
    }

    public void subscribe(FrankSubscriber subscriber) {
    }

    public void typeIntoKeyboardRequest(String text) {
    }

    public void typeIntoKeyboardResponse() {
    }

    public void unsubscribe(FrankSubscriber subscriber) {
    }
}

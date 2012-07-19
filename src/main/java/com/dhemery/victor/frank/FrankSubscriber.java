package com.dhemery.victor.frank;

import com.dhemery.victor.frankly.AccessibilityCheckResponse;
import com.dhemery.victor.frankly.MessageResponse;
import com.dhemery.victor.frankly.OrientationResponse;

public interface FrankSubscriber {
    void accessibilityCheckRequest();
    void accessibilityCheckResponse(AccessibilityCheckResponse response);
    void appExecRequest(String name, Object[] arguments);
    void appExecResponse(String name, Object[] arguments, MessageResponse response);
    void dumpRequest();
    void dumpResponse(String response);
    void mapRequest(String engine, String query, String name, Object[] arguments);
    void mapResponse(String engine, String query, String name, Object[] arguments, MessageResponse response);
    void orientationRequest();
    void orientationResponse(OrientationResponse response);
    void typeIntoKeyboardRequest(String text);
    void typeIntoKeyboardResponse();
}

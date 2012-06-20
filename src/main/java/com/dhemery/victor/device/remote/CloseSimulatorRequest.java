package com.dhemery.victor.device.remote;

import com.dhemery.victor.http.HttpRequest;

@SuppressWarnings("UnusedDeclaration")
public class CloseSimulatorRequest extends HttpRequest {
    public static final String VERB = "closeSimulator";

    public CloseSimulatorRequest() {
        super(VERB);
    }
}

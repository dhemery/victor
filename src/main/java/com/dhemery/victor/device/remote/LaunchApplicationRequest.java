package com.dhemery.victor.device.remote;

import com.dhemery.victor.http.HttpRequest;

public class LaunchApplicationRequest extends HttpRequest {
    public static final String VERB = "launchApplication";

    @SuppressWarnings("UnusedDeclaration")
    public LaunchApplicationRequest(String applicationPath) {
        super(VERB, new LaunchApplicationMessage(applicationPath));
    }
}

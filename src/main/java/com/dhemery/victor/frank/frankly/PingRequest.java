package com.dhemery.victor.frank.frankly;

import com.dhemery.victor.http.HttpRequest;


/**
 * A request with no "verb."
 * Acts as a "ping" request.
 *
 * @author Dale Emery
 */
public class PingRequest extends HttpRequest {
    public PingRequest() {
        super("");
    }
}

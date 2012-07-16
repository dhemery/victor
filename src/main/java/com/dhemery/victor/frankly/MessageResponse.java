package com.dhemery.victor.frankly;

import com.dhemery.victor.io.Response;

import java.util.Collections;
import java.util.List;

public class MessageResponse implements Response {
    private final String details;
    private final String reason;
    private final List<String> results;
    private final boolean succeeded;

    public MessageResponse(boolean succeeded, List<String> results, String reason, String details) {
        this.succeeded = succeeded;
        this.results = results;
        this.reason = reason;
        this.details = details;
    }

    public String details() { return details; }
    public String reason() { return reason; }
    public List<String> results() { return Collections.unmodifiableList(results); }
    public boolean succeeded() { return succeeded; }
}

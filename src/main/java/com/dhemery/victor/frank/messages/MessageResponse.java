package com.dhemery.victor.frank.messages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An response to an message.
 *
 * @author Dale Emery
 */
public class MessageResponse {
    private final boolean succeeded;
    private final List<String> results;
    private final String reason;
    private final String details;

    public MessageResponse(boolean succeeded, List<String> results, String reason, String details) {
        this.succeeded = succeeded;
        this.results = new ArrayList<String>(results);
        this.reason = reason;
        this.details = details;
    }

    public List<String> results() {
        return Collections.unmodifiableList(results);
    }

    public boolean failed() {
        return !succeeded;
    }

    @Override
    public String toString() {
        if (succeeded) return String.format("OK: %s", results);
        return String.format("FAILED: %s (%s)", reason, details);
    }
}

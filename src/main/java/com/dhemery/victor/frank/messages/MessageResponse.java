package com.dhemery.victor.frank.messages;

import java.util.List;

/**
 * An response to an message.
 *
 * @author Dale Emery
 */
public class MessageResponse {
    public final boolean succeeded;
    public final List<String> results;
    public final String reason;
    public final String details;

    public MessageResponse(boolean succeeded, List<String> results, String reason, String details) {
        this.succeeded = succeeded;
        this.results = results;
        this.reason = reason;
        this.details = details;
    }

    @Override
    public String toString() {
        if (succeeded) return String.format("OK: %s", results);
        return String.format("FAILED: %s (%s)", reason, details);
    }
}

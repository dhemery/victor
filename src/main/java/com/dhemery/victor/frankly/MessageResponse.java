package com.dhemery.victor.frankly;

import java.util.Collections;
import java.util.List;

//TODO: Describe success, failure, reason, and details.
/**
 * A response to a Frankly {@link Operation}.
 * @see FranklyFrank
 */
public class MessageResponse {
    private final boolean succeeded;
    private final List<String> results;
    private final String reason;
    private final String details;

    /**
     * Create a response to a Frankly operation.
     * @param succeeded whether the operation succeeded.
     * @param results the responses from the operation's recipients.
     * @param reason an explanation of the failure (if the operation failed).
     * @param details details about the failure (if the operation failed).
     */
    public MessageResponse(boolean succeeded, List<String> results, String reason, String details) {
        this.succeeded = succeeded;
        this.results = Collections.unmodifiableList(results);
        this.reason = reason;
        this.details = details;
    }

    public boolean succeeded() { return succeeded; }
    public List<String> results() { return results; }
    public String reason() { return reason; }
    public String details() { return details; }
}

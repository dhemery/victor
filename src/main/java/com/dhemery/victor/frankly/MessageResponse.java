package com.dhemery.victor.frankly;

import java.util.Collections;
import java.util.List;

//TODO: Describe success, failure, reason, and details.
/**
 * A response to a Frankly {@link Operation}.
 * @see FranklyFrank
 */
public class MessageResponse {
    private final String details;
    private final String reason;
    private final List<String> results;
    private final boolean succeeded;

    /**
     * Create a response to a Frankly operation.
     * @param succeeded whether the operation succeeded.
     * @param results the responses from the operation's recipients.
     * @param reason an explanation of the failure (if the operation failed).
     * @param details details about the failure (if the operation failed).
     */
    public MessageResponse(boolean succeeded, List<String> results, String reason, String details) {
        this.succeeded = succeeded;
        this.results = results;
        this.reason = reason;
        this.details = details;
    }

    /**
     * Report whether the operation succeeded.
     */
    public boolean succeeded() { return succeeded; }

    /**
     * The responses returned by the operation's recipients,
     * one response per recipient.
     */
    public List<String> results() { return Collections.unmodifiableList(results); }

    /**
     * An explanation of the failure (if the operation failed).
     */
    public String reason() { return reason; }

    /**
     * Additional details about the failure (if the operation failed).
     */
    public String details() { return details; }
}

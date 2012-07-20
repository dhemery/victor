package com.dhemery.victor.frankly;

import java.util.Collections;
import java.util.List;

//TODO: Describe success, failure, reason, and details.
/**
 * A response to a Frankly {@link Operation}.
 * @see PublishingFrank
 */
public class MessageResponse {
    public final String details;
    public final String reason;
    public final List<String> results;
    public final boolean succeeded;

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
}

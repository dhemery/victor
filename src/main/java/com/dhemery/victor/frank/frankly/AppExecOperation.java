package com.dhemery.victor.frank.frankly;

/**
 * An operation to send a message to the application delegate.
 * {@code AppExecOperation} is the serializable representation
 * of the arguments to {@link com.dhemery.victor.frank.Frank#appExec(String, Object...)}.
 * @see com.dhemery.victor.frank.FranklyFrank
 */
public class AppExecOperation {
    /**
     * The message to send to each recipient selected by the selector engine.
     */
    private final Operation operation;

    /**
     * Create an operation to send a message to a set of views.
     * @param operation the message to send to each recipient
     */
    public AppExecOperation(Operation operation) {
        this.operation = operation;
    }

    public Operation operation() { return operation; }
}

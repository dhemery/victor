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

    @Override
    public String toString() {
        return operation.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppExecOperation that = (AppExecOperation) o;

        if (operation != null ? !operation.equals(that.operation) : that.operation != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return operation != null ? operation.hashCode() : 0;
    }
}

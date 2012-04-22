package com.dhemery.victor.agent;

import com.dhemery.victor.view.Operation;

import java.io.IOException;

public class OperationIOException extends RuntimeException {
    public OperationIOException(Operation operation, IOException cause) {
        super(messageAbout(operation), cause);
    }

    private static String messageAbout(Operation operation) {
        return String.format("IO exception during operation:%n%s", operation.toString());
    }
}

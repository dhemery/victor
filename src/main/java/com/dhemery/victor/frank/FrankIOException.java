package com.dhemery.victor.frank;

import com.dhemery.victor.frank.Operation;

import java.io.IOException;

public class FrankIOException extends RuntimeException {
    public FrankIOException(Operation operation, IOException cause) {
        super(messageAbout(operation), cause);
    }

    private static String messageAbout(Operation operation) {
        return String.format("Operation was:%n%s", operation.toString());
    }
}

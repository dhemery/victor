package com.dhemery.victor.frank;

import com.dhemery.victor.frank.frankly.MessageResponse;
import com.dhemery.victor.frank.frankly.Operation;

public class IosOperationException extends RuntimeException {
    public IosOperationException(String target, Operation operation, MessageResponse response) {
        super(messageAbout(target, operation, response));
    }

    private static String messageAbout(String target, Operation operation, MessageResponse response) {
        return "Operation failed\n"
                + "   Target    : " + target + '\n'
                + "   Operation : " + operation.method() + ' ' + operation.arguments() + '\n'
                + "   Reason    : " + response.reason() + '\n'
                + "   Details   : " + response.details();
    }
}

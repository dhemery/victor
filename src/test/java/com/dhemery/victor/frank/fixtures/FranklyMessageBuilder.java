package com.dhemery.victor.frank.fixtures;

import com.dhemery.victor.frank.frankly.AppExecOperation;
import com.dhemery.victor.frank.frankly.MessageResponse;
import com.dhemery.victor.frank.frankly.Operation;

import java.util.Arrays;
import java.util.Collections;

public class FranklyMessageBuilder {

    public static AppExecOperation operation(String methodName, Object... parameters) {
        return new AppExecOperation(new Operation(methodName, parameters));
    }

    public static MessageResponse messageResponse(String... result) {
        return new MessageResponse(true, Arrays.asList(result), null, null);
    }

    public static MessageResponse failureMessageResponse() {
        return new MessageResponse(false, Collections.<String>emptyList(), "ignored failure reason", "ignored failure details");
    }
}

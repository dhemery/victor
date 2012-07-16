package com.dhemery.victor.frankly;

import java.io.Serializable;

public class Operation implements Serializable {
    private final String method;
    private final Object[] arguments;

    public Operation(String method, Object... arguments) {
        this.method = method;
        this.arguments = arguments;
    }

    public String method() { return method; }
    public Object[] arguments() { return arguments.clone(); }
}

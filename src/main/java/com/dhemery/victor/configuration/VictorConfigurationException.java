package com.dhemery.victor.configuration;

public class VictorConfigurationException extends RuntimeException {
    public VictorConfigurationException(String explanation) {
        super(explanation);
    }

    public VictorConfigurationException(String explanation, Throwable cause) {
        super(explanation, cause);
    }
}

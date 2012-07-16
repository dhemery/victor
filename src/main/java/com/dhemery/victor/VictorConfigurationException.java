package com.dhemery.victor;

/**
 * Reports that an element of the Victor configuration is invalid.
 * The error might be in the configuration options
 * or in the operating system environment.
 */
public class VictorConfigurationException extends RuntimeException {
    public VictorConfigurationException(String explanation) {
        super(explanation);
    }

    public VictorConfigurationException(String explanation, Throwable cause) {
        super(explanation, cause);
    }
}

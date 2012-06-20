package com.dhemery.victor.configuration;

public class IosDeviceConfigurationException extends RuntimeException {
    public IosDeviceConfigurationException(String explanation) {
        super(explanation);
    }

    @SuppressWarnings("UnusedDeclaration")
    public IosDeviceConfigurationException(String explanation, Throwable cause) {
        super(explanation, cause);
    }
}

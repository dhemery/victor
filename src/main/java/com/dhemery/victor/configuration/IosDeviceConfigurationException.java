package com.dhemery.victor.configuration;

import java.io.IOException;

public class IosDeviceConfigurationException extends RuntimeException {
    public IosDeviceConfigurationException(String explanation) {
        super(explanation);
    }

    public IosDeviceConfigurationException(String explanation, Throwable cause) {
        super(explanation, cause);
    }
}

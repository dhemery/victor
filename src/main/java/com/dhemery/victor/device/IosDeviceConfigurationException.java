package com.dhemery.victor.device;

import java.io.IOException;

public class IosDeviceConfigurationException extends RuntimeException {
    public IosDeviceConfigurationException(String explanation) {
        super(explanation);
    }

    public IosDeviceConfigurationException(String explanation, IOException cause) {
        super(explanation, cause);
    }
}

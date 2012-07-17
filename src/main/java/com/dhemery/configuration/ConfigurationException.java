package com.dhemery.configuration;

/**
 * Reports that an element of a configuration is invalid.
 */
public class ConfigurationException extends RuntimeException {
    public ConfigurationException(String explanation) {
        super(explanation);
    }
}

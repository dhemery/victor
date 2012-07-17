package com.dhemery.configuration;

/**
 * Reports that an element of the Victor configuration is invalid.
 * The error might be in the configuration options
 * or in the operating system environment.
 */
public class ConfigurationException extends RuntimeException {
    public ConfigurationException(String explanation) {
        super(explanation);
    }
}

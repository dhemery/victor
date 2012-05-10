package com.dhemery.victor.frank;

/**
 * Configuration options for {@link CreateIosApplication}.
 */
public class IosApplicationConfigurationOptions {
    private IosApplicationConfigurationOptions(){}

    /**
     * The name of the host on which the Frank server listens for requests.
     * Do not include a scheme (e.g. "http://") at the start of this value.
     */
    public static final String HOST = "victor.frank.host";

    /**
     * The port on which the Frank server listens for requests.
     */
    public static final String PORT = "victor.frank.port";
}

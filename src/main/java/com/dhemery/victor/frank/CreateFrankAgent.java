package com.dhemery.victor.frank;

import java.util.Properties;

/**
 * Factory methods to create Frank agents.
 */
public class CreateFrankAgent {
    /**
     * The value of {@link #FRANK_HOST_PROPERTY_NAME} if the user does not supply a value.
     */
    public static final String DEFAULT_FRANK_HOST = "localhost";

    /**
     * The value of {@link #FRANK_PORT_PROPERTY_NAME} if the user does not supply a value.
     */
    public static final String DEFAULT_FRANK_PORT = "37265";

    /**
     * Specifies the name of the host on which the Frank server listens for requests.
     * Do not include a scheme (e.g. "http://") at the start of this value.
     */
    public static final String FRANK_HOST_PROPERTY_NAME = "victor.frank.host";

    /**
     * Specifies the port on which the Frank server listens for requests.
     */
    public static final String FRANK_PORT_PROPERTY_NAME = "victor.frank.port";

    /**
     * Create a Frank agent that interacts with a Frank server
     * at the default {@link #DEFAULT_FRANK_HOST host}
     * and {@link #DEFAULT_FRANK_PORT port}.
     *
     * @return the Frank agent.
     */
    public static FrankAgent forDefaultFrankServerUrl() {
        return fromProperties(new Properties());
    }

    /**
     * Create a Frank agent that interacts with the Frank server
     * at the given URL.
     *
     * @param url the URL at which the Frank server listens.
     * @return the Frank agent.
     */
    public static FrankAgent forFrankServerUrl(String url) {
        return new FrankAgent(url);
    }

    /**
     * Create a Frank agent that interacts with the Frank server
     * at the given HTTP host and port.
     * The host name must not include the HTTP scheme (e.g. "http://").
     *
     * @param host the name of the Frank server's host.
     * @param port the port on which the Frank server listens.
     * @return the Frank agent.
     */
    public static FrankAgent forFrankServerUrl(String host, Long port) {
        return forFrankServerUrl(makeUrl(host, port));
    }

    /**
     * Create a Frank agent that interacts with the Frank server
     * at a URL designated by property values.
     * See the field descriptions further information.
     *
     * @param properties properties that specify the URL at which the Frank server listens.
     * @return the Frank agent.
     */
    // todo Make this take a map.
    public static FrankAgent fromProperties(Properties properties) {
        return forFrankServerUrl(makeUrl(hostProperty(properties), Long.parseLong(portProperty(properties))));
    }

    private static String hostProperty(Properties properties) {
        return properties.getProperty(FRANK_HOST_PROPERTY_NAME, DEFAULT_FRANK_HOST);
    }

    private static String portProperty(Properties properties) {
        return properties.getProperty(FRANK_PORT_PROPERTY_NAME, DEFAULT_FRANK_PORT);
    }

    private static String makeUrl(String host, Long port) {
        return String.format("http://%s:%s", host, port);
    }
}

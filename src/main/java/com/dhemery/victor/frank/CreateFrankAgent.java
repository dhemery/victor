package com.dhemery.victor.frank;

import java.util.Properties;

/**
 * Factory methods to create Frank agents.
 */
public class CreateFrankAgent {
    public static final String VICTOR_FRANK_SERVER_PORT_PROPERTY = "victor.frank.server.port";
    public static final String VICTOR_APPLICATION_HOST_PROPERTY = "victor.application.host";
    public static final String DEFAULT_FRANK_SERVER_DOMAIN_NAME = "localhost";
    public static final Long DEFAULT_FRANK_SERVER_PORT = 37265L;

    /**
     * Create a Frank agent that interacts with a Frank server
     * at the default {@link #DEFAULT_FRANK_SERVER_DOMAIN_NAME host}
     * and {@link #DEFAULT_FRANK_SERVER_PORT port}.
     * @return the Frank agent.
     */
    public static FrankAgent forDefaultFrankServerUrl() {
        return forFrankServerUrl(DEFAULT_FRANK_SERVER_DOMAIN_NAME, DEFAULT_FRANK_SERVER_PORT);
    }

    /**
     * Creates a Frank agent that interacts with the Frank server
     * at the given URL.
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
     * @param host the name of the Frank server's host.
     * @param port the port on which the Frank server listens.
     * @return the Frank agent.
     */
    public static FrankAgent forFrankServerUrl(String host, Long port) {
        return forFrankServerUrl(makeUrl(host, port));
    }

    /**
     * <p>Create a Frank agent that interacts with the Frank server
     * at a URL designated by property values.
     *</p>
     * <p>
     * The host name is specified by the property {@code victor.application.host}.
     * The host name must not include the HTTP scheme (e.g. "http://").
     *</p>
     * <p>
     * The port number is specified by the property {@code victor.frank.server.port}.
     * The port must be parseable by {@link Long#parseLong(String)}.
     *</p>
     * @param properties properties that specify the URL at which the Frank server listens
     * @return the Frank agent.
     */
    // todo It's probably a bad idea to bind users to particular properties.
    public static FrankAgent fromProperties(Properties properties) {
        return forFrankServerUrl(makeUrl(hostProperty(properties), portProperty(properties)));
    }

    // todo Throw if there is no such property.
    private static String hostProperty(Properties properties) {
        return properties.getProperty(VICTOR_APPLICATION_HOST_PROPERTY);
    }

    private static String makeUrl(String host, Long port) {
        return String.format("http://%s:%s", host, port);
    }

    // todo Throw if there is no such property, or if its value cannot be parsed.
    private static Long portProperty(Properties properties) {
        return Long.parseLong(properties.getProperty(VICTOR_FRANK_SERVER_PORT_PROPERTY));
    }
}

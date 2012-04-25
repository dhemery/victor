package com.dhemery.victor.frank;

import java.util.Properties;

/**
 * Factory methods to create Frank agents.
 */
public class CreateFrankAgent {
    public static final String FRANK_PORT = "victor.frank.port";
    public static final String FRANK_HOST = "victor.frank.host";
    public static final String DEFAULT_FRANK_HOST = "localhost";
    public static final Long DEFAULT_FRANK_PORT = 37265L;

    /**
     * Create a Frank agent that interacts with a Frank server
     * at the default {@link #DEFAULT_FRANK_HOST host}
     * and {@link #DEFAULT_FRANK_PORT port}.
     * @return the Frank agent.
     */
    public static FrankAgent forDefaultFrankServerUrl() {
        return forFrankServerUrl(DEFAULT_FRANK_HOST, DEFAULT_FRANK_PORT);
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
        return property(properties, FRANK_HOST);
    }

    // todo Throw if there is no such property, or if its value cannot be parsed.
    private static Long portProperty(Properties properties) {
        return longProperty(properties, FRANK_PORT);
    }

    private static String makeUrl(String host, Long port) {
        return String.format("http://%s:%s", host, port);
    }

    private static Long longProperty(Properties properties, String propertyName) {
        return Long.parseLong(property(properties, propertyName));
    }

    private static String property(Properties properties, String propertyName) {
        if(!properties.stringPropertyNames().contains(propertyName)) {
            throw new RuntimeException("Missing value for property " + propertyName);
        }
        return properties.getProperty(propertyName);
    }
}

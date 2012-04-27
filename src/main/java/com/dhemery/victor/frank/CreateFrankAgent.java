package com.dhemery.victor.frank;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.dhemery.victor.frank.FrankAgentConfigurationProperties.HOST;
import static com.dhemery.victor.frank.FrankAgentConfigurationProperties.PORT;

/**
 * Factory methods to create Frank agents.
 */
public class CreateFrankAgent {
    /**
     * The value of the {@link FrankAgentConfigurationProperties#HOST HOST} property
     * if the user does not supply a value.
     */
    public static final String DEFAULT_FRANK_HOST = "localhost";

    /**
     * The value of the {@link FrankAgentConfigurationProperties#PORT PORT} property
     * if the user does not supply a value.
     */
    public static final String DEFAULT_FRANK_PORT = "37265";

    private static final Map<String, String> DEFAULTS = new HashMap<String, String>();

    static {
        DEFAULTS.put(HOST, DEFAULT_FRANK_HOST);
        DEFAULTS.put(PORT, DEFAULT_FRANK_PORT);
    }

    /**
     * Create a Frank agent that interacts with a Frank server
     * at the default host and port.
     *
     * @return the Frank agent.
     * @see #DEFAULT_FRANK_HOST
     * @see #DEFAULT_FRANK_PORT
     */
    public static FrankAgent forDefaultFrankServerUrl() {
        return withConfiguration(Collections.<String, String>emptyMap());
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
     * <p>Create a Frank agent that interacts with the Frank server
     * at a URL designated by configuration property values.
     * </p>
     * <p>
     * This method provides default values for properties
     * whose values are not defined in the given {@code configuration}.
     * </p>
     *
     * @param configuration map that specify the properties for the Frank agent.
     * @return the Frank agent.
     * @see FrankAgentConfigurationProperties
     * @see #DEFAULT_FRANK_HOST
     * @see #DEFAULT_FRANK_PORT
     */
    public static FrankAgent withConfiguration(Map<String, String> configuration) {
        Map<String, String> properties = new HashMap<String, String>();
        properties.putAll(DEFAULTS);
        properties.putAll(configuration);

        String host = properties.get(HOST);
        String port = properties.get(PORT);
        String url = String.format("http://%s:%s", host, port);
        return forFrankServerUrl(url);
    }
}

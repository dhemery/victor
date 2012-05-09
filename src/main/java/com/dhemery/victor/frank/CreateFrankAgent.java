package com.dhemery.victor.frank;

import com.dhemery.victor.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.dhemery.victor.frank.FrankAgentConfigurationOptions.HOST;
import static com.dhemery.victor.frank.FrankAgentConfigurationOptions.PORT;

/**
 * Factory methods to create Frank agents.
 */
public class CreateFrankAgent {
    /**
     * The value of the {@link FrankAgentConfigurationOptions#HOST HOST} property
     * if the user does not supply a value.
     */
    public static final String DEFAULT_FRANK_HOST = "localhost";

    /**
     * The value of the {@link FrankAgentConfigurationOptions#PORT PORT} property
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
        return withConfiguration(new Configuration());
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
     * at a URL designated by a configuration.
     * </p>
     * <p>
     * This method provides default values for options
     * whose values are not defined in the given {@code configuration}.
     * </p>
     *
     * @param userConfiguration configuration options for the Frank agent.
     * @return the Frank agent.
     * @see FrankAgentConfigurationOptions
     * @see #DEFAULT_FRANK_HOST
     * @see #DEFAULT_FRANK_PORT
     */
    public static FrankAgent withConfiguration(Configuration userConfiguration) {
        Configuration configuration = new Configuration(DEFAULTS);
        configuration.merge(userConfiguration);

        String host = configuration.option(HOST);
        String port = configuration.option(PORT);
        String url = String.format("http://%s:%s", host, port);
        return forFrankServerUrl(url);
    }
}

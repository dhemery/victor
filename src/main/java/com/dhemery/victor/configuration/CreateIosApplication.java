package com.dhemery.victor.configuration;

import com.dhemery.victor.IosApplication;
import com.dhemery.victor.frank.FrankAgent;
import com.dhemery.victor.frank.FrankApplicationAgent;
import com.dhemery.victor.frank.FrankIosApplication;

import static com.dhemery.victor.configuration.IosApplicationConfigurationOptions.*;

/**
 * <p>
 * Create a {@link FrankIosApplication} configured according to a set of configuration options.
 * See {@link IosApplicationConfigurationOptions} for descriptions, property names, and default values for the available options.
 * </p>
 * </table>
 */
public class CreateIosApplication {
    private final Configuration configuration = defaultConfiguration();

    private CreateIosApplication(Configuration configuration) {
        this.configuration.merge(configuration);
    }

    /**
     * <p>
     * Create a {@link FrankIosApplication} configured according to {@code configuration}.
     * @param configuration specifies the configuration options.
     * @return a {@link FrankIosApplication} configured as specified.
     */
    public static IosApplication withConfiguration(Configuration configuration) {
        return new CreateIosApplication(configuration).application();
    }

    private IosApplication application() {
        return new FrankIosApplication(frankAgent());
    }

    private Configuration defaultConfiguration() {
        Configuration defaultConfiguration = new Configuration();
        defaultConfiguration.set(FRANK_HOST, DEFAULT_FRANK_HOST);
        defaultConfiguration.set(FRANK_PORT, DEFAULT_FRANK_PORT);
        return defaultConfiguration;
    }

    private FrankApplicationAgent frankAgent() {
        String host = configuration.option(FRANK_HOST);
        String port = configuration.option(FRANK_PORT);
        String url = String.format("http://%s:%s", host, port);
        return new FrankAgent(url);
    }
}

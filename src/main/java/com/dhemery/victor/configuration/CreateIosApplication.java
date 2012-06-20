package com.dhemery.victor.configuration;

import com.dhemery.configuration.Configuration;
import com.dhemery.victor.IosApplication;
import com.dhemery.victor.VictorEntryPoint;
import com.dhemery.victor.frank.FrankAgent;
import com.dhemery.victor.frank.FrankIosApplication;

/**
 * <p>
 * Create a {@link FrankIosApplication} configured according to a set of configuration options.
 * See individual fields for descriptions, property names, and default values for the available options.
 * </p>
 * </table>
 */
@SuppressWarnings("UnusedDeclaration")
public class CreateIosApplication {
    /**
     * The value of the {@link #FRANK_HOST} option
     * if the user does not supply a value.
     */
    public static final String DEFAULT_FRANK_HOST = "localhost";

    /**
     * The value of the {@link #FRANK_PORT} option
     * if the user does not supply a value.
     */
    public static final String DEFAULT_FRANK_PORT = "37265";

    /**
     * The name of the host on which the Frank server listens for requests.
     * Do not include a scheme (e.g. "http://") at the start of this value.
     */
    public static final String FRANK_HOST = "victor.frank.host";

    /**
     * The port on which the Frank server listens for requests.
     */
    public static final String FRANK_PORT = "victor.frank.port";

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
    @VictorEntryPoint
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

    private FrankAgent frankAgent() {
        String host = configuration.option(FRANK_HOST);
        String port = configuration.option(FRANK_PORT);
        String url = String.format("http://%s:%s", host, port);
        return new FrankAgent(url);
    }
}

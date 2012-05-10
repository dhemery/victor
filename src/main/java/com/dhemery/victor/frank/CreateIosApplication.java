package com.dhemery.victor.frank;

import com.dhemery.victor.Configuration;
import com.dhemery.victor.IosApplication;

import static com.dhemery.victor.frank.IosApplicationConfigurationOptions.HOST;
import static com.dhemery.victor.frank.IosApplicationConfigurationOptions.PORT;

/**
 * Create a {@link FrankIosApplication} configured according to a set of configuration options.
 */
public class CreateIosApplication {
    /**
     * The value of the {@link IosApplicationConfigurationOptions#HOST HOST} option
     * if the user does not supply a value.
     */
    public static final String DEFAULT_FRANK_HOST = "localhost";

    /**
     * The value of the {@link IosApplicationConfigurationOptions#PORT PORT} option
     * if the user does not supply a value.
     */
    public static final String DEFAULT_FRANK_PORT = "37265";

    private final Configuration configuration = defaultConfiguration();

    private CreateIosApplication(Configuration configuration) {
        this.configuration.merge(configuration);
    }

    /**
     * <p>
     * Create a {@link FrankIosApplication} configured according to {@code configuration}.
     * See {@link IosApplicationConfigurationOptions} for names and descriptions of the available options.
     * </p>
     * <p>This method supplies default values for undefined configuration options as follows:</p>
     * <table>
     * <tr><th style="text-align:left">Option</th><th style="text-align:left">Default value</th></tr>
     * <tr>
     * <td>{@link IosApplicationConfigurationOptions#HOST HOST}</td>
     * <td>The value of {@link #DEFAULT_FRANK_HOST}</td>
     * </tr>
     * <tr>
     * <td>{@link IosApplicationConfigurationOptions#PORT PORT}</td>
     * <td>The value of {@link #DEFAULT_FRANK_PORT}</td>
     * </tr>
     * </table>
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
        defaultConfiguration.set(HOST, DEFAULT_FRANK_HOST);
        defaultConfiguration.set(PORT, DEFAULT_FRANK_PORT);
        return defaultConfiguration;
    }

    private FrankApplicationAgent frankAgent() {
        String host = configuration.option(HOST);
        String port = configuration.option(PORT);
        String url = String.format("http://%s:%s", host, port);
        return new FrankAgent(url);
    }
}

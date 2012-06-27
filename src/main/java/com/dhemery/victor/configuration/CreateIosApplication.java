package com.dhemery.victor.configuration;

import com.dhemery.configuration.Configuration;
import com.dhemery.victor.IosApplication;
import com.dhemery.victor.frank.FrankAgent;
import com.dhemery.victor.frank.FrankIosApplication;

/**
 * <p>
 * Create a {@link FrankIosApplication} configured according to a set of configuration options.
 * See individual fields for descriptions, property names, and default values for the available options.
 * </p>
 * </table>
 */
public class CreateIosApplication {
    private final VictorConfiguration configuration;

    private CreateIosApplication(Configuration configuration) {
        this.configuration = new VictorConfiguration(configuration);
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

    private FrankAgent frankAgent() {
        String url = String.format("http://%s:%s", configuration.frankHost(), configuration.frankPort());
        return new FrankAgent(url);
    }
}

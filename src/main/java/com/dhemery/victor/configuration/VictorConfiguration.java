package com.dhemery.victor.configuration;

import com.dhemery.configuration.Configuration;

import java.util.List;

public class VictorConfiguration {
    /**
     * The absolute path to the iOS application bundle to execute.
     * This is typically the application's .app package.
     */
    public static final String APPLICATION_BUNDLE_PATH = "victor.application.bundle.path";

    /**
     * The default value for the {@link #DEVICE_TYPE} option.
     */
    public static final String DEFAULT_DEVICE_TYPE = "iPhone";

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
     * The default value for the {@link #SIMULATOR_PROCESS_OWNER} option.
     */
    public static final String DEFAULT_SIMULATOR_PROCESS_OWNER = "victor";

    /**
     * The type of device to simulate.
     * See your iOS Simulator's <em>Hardware > Device</em> menu for possible values.
     */
    public static final String DEVICE_TYPE = "victor.simulator.device.type";

    /**
     * The name of the host on which the Frank server listens for requests.
     * Do not include a scheme (e.g. "http://") at the start of this value.
     */
    public static final String FRANK_HOST = "victor.frank.host";

    /**
     * The port on which the Frank server listens for requests.
     */
    public static final String FRANK_PORT = "victor.frank.port";

    /**
     * The version of SDK to use to run the simulator.
     */
    public static final String SDK_VERSION = "victor.simulator.sdk.version";

    /**
     * <p>Specifies who is responsible for starting and stopping the simulator.</p>
     * <p>If the value is <strong>victor</strong>,
     * the constructed {@link com.dhemery.victor.IosDevice IosDevice}'s
     * {@link com.dhemery.victor.IosDevice#start() start()} method will launch the simulator,
     * and its {@link com.dhemery.victor.IosDevice#stop() stop()} method will shut it down.
     * </p>
     * <p>
     * If this option has any other value,
     * the constructed {@link com.dhemery.victor.IosDevice IosDevice}
     * will neither start nor stop the simulator.
     * Instead, the user must start and stop the simulator in some other way.
     * This is useful for experimenting.
     * It allows you to launch the application on your own,
     * manually execute preparatory steps to bring the application to a desired state,
     * then run an automated test against the prepared application.
     * </p>
     */
    public static final String SIMULATOR_PROCESS_OWNER = "victor.simulator.process.owner";

    private final Configuration configuration;
    private final IosApplicationBundle applicationBundle;

    public VictorConfiguration(Configuration configuration) {
        this.configuration = configuration;
        applicationBundle = new IosApplicationBundle(configuration.requiredOption(APPLICATION_BUNDLE_PATH));
    }

    public IosApplicationBundle applicationBundle() {
        return applicationBundle;
    }

    private String defaultDeviceType() {
        List<String> deviceTypes = applicationBundle.deviceTypes();
        if(deviceTypes.size() == 1) return deviceTypes.get(0);
        return DEFAULT_DEVICE_TYPE;
    }

    public String deviceType() {
        return property(DEVICE_TYPE, defaultDeviceType());
    }

    public String frankHost() {
        return property(FRANK_HOST, DEFAULT_FRANK_HOST);
    }

    public long frankPort() {
        return Long.parseLong(property(FRANK_PORT, DEFAULT_FRANK_PORT));
    }

    private String property(String property, String defaultValue) {
        if(!configuration.defines(property)) {
            configuration.set(property, defaultValue);
        }
        return configuration.option(property);
    }

    public IosSdk sdk() {
        if(configuration.defines(SDK_VERSION)) {
            String version = configuration.option(SDK_VERSION);
            IosSdk sdk = IosSdk.withVersion(version);
            if(sdk.isInstalled()) return sdk;
        }

        IosApplicationBundle bundle = new IosApplicationBundle(configuration.requiredOption(APPLICATION_BUNDLE_PATH));
        String canonicalName = bundle.sdkCanonicalName();
        if(canonicalName != null) {
            IosSdk sdk = IosSdk.withCanonicalName(canonicalName);
            if(sdk.isInstalled()) return sdk;
        }

        return IosSdk.newest();
    }

    public boolean victorOwnsSimulator() {
        String processOwner = property(SIMULATOR_PROCESS_OWNER, DEFAULT_SIMULATOR_PROCESS_OWNER);
        return processOwner.equals(DEFAULT_SIMULATOR_PROCESS_OWNER);
    }
}

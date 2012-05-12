package com.dhemery.victor.configuration;

import com.dhemery.victor.IosDevice;

/**
 * Configuration options for {@link CreateIosDevice}.
 */
public class IosDeviceConfigurationOptions {
    private IosDeviceConfigurationOptions(){}

    /**
     * The absolute path to the iOS application binary file to execute.
     * This is typically a file inside the application's .app package.
     * The file's executable flag must be set.
     */
    public static final String APPLICATION_BINARY_PATH = "victor.application.binary.path";

    /**
     * The default value for the {@link #DEVICE_TYPE} option.
     */
    public static final String DEFAULT_DEVICE_TYPE = "iPhone";

    /**
     * The default value for the {@link #SDK_VERSION} option,
     * obtained by calling {@link XcodeBuild#newestInstalledSdkVersion()}.
     */
    public static final String DEFAULT_SDK_VERSION = XcodeBuild.newestInstalledSdkVersion();

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
     * The version of the SDK with which to launch the simulator.
     */
    public static final String SDK_VERSION = "victor.simulator.sdk.version";

    /**
     * <p>Specifies who is responsible for starting and stopping the simulator.</p>
     * <p>If the value is <strong>victor</strong>,
     * the constructed {@link IosDevice IosDevice}'s
     * {@link IosDevice#start() start()} method will launch the simulator,
     * and its {@link IosDevice#stop() stop()} method will shut it down.
     * </p>
     * <p>
     * If this option has any other value,
     * the constructed {@link IosDevice IosDevice}
     * will neither start nor stop the simulator.
     * Instead, the user must start and stop the simulator in some other way.
     * This is useful for experimenting.
     * It allows you to launch the application on your own,
     * manually execute preparatory steps to bring the application to a desired state,
     * then run an automated test against the prepared application.
     * </p>
     */
    public static final String SIMULATOR_PROCESS_OWNER = "victor.simulator.process.owner";
}

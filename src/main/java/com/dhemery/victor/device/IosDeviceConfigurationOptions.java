package com.dhemery.victor.device;

public class IosDeviceConfigurationOptions {
    /**
     * The path to the iOS application binary file to execute.
     * This is typically a file inside the application's .app package.
     * The file's executable flag must be setOption.
     */
    public static final String APPLICATION_BINARY_PATH = "victor.application.binary.path";

    /**
     * The type of device to simulate.
     * See your iOS Simulator's Device menu for possible values.
     */
    public static final String DEVICE_TYPE = "victor.simulator.device.type";

    /**
     * The path to the root directory of the iOS SDK with which to launch the simulator.
     */
    public static final String SDK_ROOT = "victor.sdk.root";

    /**
     * The path to the iOS Simulator executable file.
     */
    public static final String SIMULATOR_BINARY_PATH = "victor.simulator.binary.path";

    /**
     * Who launches and shuts down the simulator: <strong>victor</strong> or <strong>user</strong>.
     */
    public static final String SIMULATOR_PROCESS_OWNER = "victor.simulator.process.owner";
}

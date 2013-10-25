package com.dhemery.victor.build;

import com.dhemery.configuring.Configuration;
import com.dhemery.configuring.ConfigurationException;
import com.dhemery.core.Builder;
import com.dhemery.network.CodecEndpoint;
import com.dhemery.network.Endpoint;
import com.dhemery.network.SerializingEndpoint;
import com.dhemery.network.URLEndpoint;
import com.dhemery.os.ProcessBuilderShell;
import com.dhemery.os.PublishingShell;
import com.dhemery.os.Shell;
import com.dhemery.publishing.NullPublisher;
import com.dhemery.publishing.Publisher;
import com.dhemery.serializing.Codec;
import com.dhemery.victor.*;
import com.dhemery.victor.device.*;
import com.dhemery.victor.discovery.FileSystemIosApplicationBundle;
import com.dhemery.victor.discovery.FileSystemIosSdk;
import com.dhemery.victor.frank.Frank;
import com.dhemery.victor.frank.FrankApplication;
import com.dhemery.victor.frank.FranklyFrank;
import com.dhemery.victor.frank.PublishingFrank;
import com.dhemery.victor.frank.frankly.FranklyJsonCodec;

import java.util.ArrayList;
import java.util.List;

/**
 * Build a Victor environment configured according to specified configuration options.
 * <p>The configuration options are:</p>
 *
 * <p><strong>{@code victor.application.bundle.path}:</strong>
 * The absolute path to the application's .app bundle.
 * </p>
 * <blockquote>Default Value:
 * None.
 * Victor cannot guess the path to your application.
 * </blockquote>
 *
 * <p><strong>{@code victor.frank.host}:</strong>
 * The host on which the Frank server listens for requests.
 * </p>
 * <blockquote>Default value: {@code localhost}</blockquote>
 *
 * <p><strong>{@code victor.frank.port}:</strong>
 * The port on which the Frank server listens for requests.
 * </p>
 * <blockquote>Default value: {@code 37265}</blockquote>
 *
 * <p><strong>{@code victor.simulator.launcher}</strong>:
 * The name of the launcher to launch the application in the simulator.
 * </p>
 * <blockquote>Possible Values:
 * <ul>
 *     <li>{@code instruments}:
 *          Victor will launch the iOS simulator application via <em>instruments</em>.
 *     </li>
 *     <li>{@code victor-3.0}:
 *          Victor will launch the simulator directly using command line commands.
 *          The {@code victor-3.0} launcher requires Xcode 4.
 *          It does not work with Xcode 5.
 *     </li>
 *     <li><em>Any other value</em>:
 *          Victor will neither start nor stop the simulator.
 *          Instead, the user must start and stop the simulator in some other way.
 *          This is useful for experimenting.
 *          It allows you to launch the application on your own,
 *          manually execute preparatory steps to bring the application to a desired state,
 *          then run an automated test against the prepared application.
 *     </li>
 * </ul>
 * </blockquote>
 * <blockquote>Default Value: {@code instruments}</blockquote>
 * </ul>
 *
 * <p><strong>{@code victor.simulator.device.type}</strong>:
 * The type of device to simulate.
 * </p>
 * <blockquote>Possible Values: Any value that appears in the simulator's Hardware/Device menu.</blockquote>
 * <blockquote>Default Value: {@code iPhone}</blockquote>
 *
 * <p><strong>{@code victor.simulator.sdk.version}</strong>:
 * The SDK version to simulate.
 * <blockquote>Possible values: Any installed SDK version number.</blockquote>
 * <blockquote>Default value:
 * <ul>
 *     <li>If the application bundle specifies an SDK version, Victor uses that by default.</li>
 *     <li>Otherwise, Victor uses the latest installed SDK as the default value.</li>
 * </ul>
 *</blockquote>
 *
 * <blockquote><strong>WARNING:</strong>
 * The {@code instruments} launcher disregards this option.
 * It always uses the latest installed SDK.
 * </blockquote>
 */
public class ConfiguredVictorBuilder implements Builder<Victor> {
    private static final String DEVICE_TYPE_DEFAULT = "iPhone";
    private static final String FRANK_ENDPOINT_PROTOCOL = "http";
    private static final String FRANK_HOST_DEFAULT = "localhost";
    private static final String FRANK_PORT_DEFAULT = "37265";
    private static final String INSTRUMENTS_SIMULATOR_LAUNCHER = "instruments";
    private static final String SIMULATOR_LAUNCHER_DEFAULT = INSTRUMENTS_SIMULATOR_LAUNCHER;
    private static final String VICTOR_3_0_SIMULATOR_LAUNCHER = "victor-3.0";

    /**
     * This option specifies
     * the absolute path to the iOS application's .app package.
     */
    public static final String APPLICATION_BUNDLE_PATH = "victor.application.bundle.path";

    /**
     * This option specifies
     * the type of device to simulate.
     */
    public static final String DEVICE_TYPE = "victor.simulator.device.type";

    /**
     * This option specifies
     * the host on which the Frank server listens for requests.
     */
    public static final String FRANK_HOST = "victor.frank.host";

    /**
     * This option specifies
     * the port on which the Frank server listens for requests.
     */
    public static final String FRANK_PORT = "victor.frank.port";

    /**
     * This option specifies
     * the version of SDK to simulate.
     */
    public static final String SDK_VERSION = "victor.simulator.sdk.version";

    /**
     * This option specifies
     * the launcher for starting and stopping the simulator.
     */
    public static final String SIMULATOR_LAUNCHER = "victor.simulator.launcher";

    private IosApplication application;
    private IosApplicationBundle applicationBundle;
    private final Configuration configuration;
    private IosDevice device;
    private String deviceType;
    private Frank frank;
    private Publisher publisher;
    private IosSdk sdk;
    private Shell shell;
    private Service simulatorService;

    private ConfiguredVictorBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * Create a Victor builder with the specified configuration options.
     * @param configuration the configuration options for Victor
     */
    public static ConfiguredVictorBuilder victorFromConfiguration(Configuration configuration) {
        return new ConfiguredVictorBuilder(configuration);
    }

    /**
     * Supply a publisher for use by the objects created by this builder.
     * @return this builder
     */
    public ConfiguredVictorBuilder withPublisher(Publisher publisher) {
        this.publisher = publisher;
        return this;
    }

    @Override
    public Victor build() {
        return new DefaultVictor(application(), applicationBundle(), device(), sdk());
    }

    private IosApplicationBundle applicationBundle() {
        if(applicationBundle == null) applicationBundle = new FileSystemIosApplicationBundle(configuration.requiredOption(APPLICATION_BUNDLE_PATH), shell());
        return applicationBundle;
    }

    private IosApplication application() {
        if(application == null) application = new FrankApplication(frank());
        return application;
    }

    private String defaultDeviceType() {
        List<String> deviceTypes = applicationBundle().deviceTypes();
        return deviceTypes.size() == 1 ? deviceTypes.get(0) : DEVICE_TYPE_DEFAULT;
    }

    private IosDevice device() {
        if(device == null) device = new SimulatedIosDevice(deviceType(), simulatorApplication(), simulatorService());
        return device;
    }

    private String deviceType() {
        if(deviceType == null) deviceType = option(DEVICE_TYPE, defaultDeviceType());
        return deviceType;
    }

    private IosSdk firstInstalledSdk(List<String> prioritizedSdkNames) {
        for(String sdkName : prioritizedSdkNames) {
            if(sdkName == null) continue;
            IosSdk sdk = new FileSystemIosSdk(sdkName, shell);
            if(sdk.isInstalled()) return sdk;
        }
        throw new ConfigurationException("No iphonesimulator SDK installed on this computer");
    }

    private Frank frank() {
        if(frank == null) {
            String host = option(FRANK_HOST, FRANK_HOST_DEFAULT);
            int port = Integer.parseInt(option(FRANK_PORT, FRANK_PORT_DEFAULT));
            Endpoint endpoint = new URLEndpoint(FRANK_ENDPOINT_PROTOCOL, host, port);
            Codec codec = new FranklyJsonCodec();
            SerializingEndpoint franklyEndpoint = new CodecEndpoint(endpoint, codec);
            frank = new PublishingFrank(publisher(), new FranklyFrank(franklyEndpoint));
        }
        return frank;
    }

    private String option(String property, String defaultValue) {
        if(!configuration.defines(property)) {
            configuration.define(property, defaultValue);
        }
        return configuration.option(property);
    }

    private List<String> prioritizedSdkNames() {
        List<String> prioritizedSdkNames = new ArrayList<>();
        // Priority 1: The SDK version specified in the configuration file, if any
        if(configuration.defines(SDK_VERSION)) {
            prioritizedSdkNames.add("iphonesimulator" + configuration.option(SDK_VERSION));
        }

        // Priority 2: The SDK version specified in the application bundle
        prioritizedSdkNames.add(applicationBundle().targetSdkCanonicalName());

        // Priority 3: The latest installed SDK
        prioritizedSdkNames.add("iphonesimulator");
        return prioritizedSdkNames;
    }

    private Publisher publisher() {
        if(publisher == null) publisher = new NullPublisher();
        return publisher;
    }

    private IosSdk sdk() {
        if(sdk == null) sdk = firstInstalledSdk(prioritizedSdkNames());
        return sdk;
    }

    private Shell shell() {
        if(shell == null) shell = new PublishingShell(publisher(), new ProcessBuilderShell());
        return shell;
    }

    private SimulatorApplication simulatorApplication() {
        return new SimulatorApplication(shell());
    }

    private Service simulatorService() {
        if(simulatorService == null) {
            String launcherType = option(SIMULATOR_LAUNCHER, SIMULATOR_LAUNCHER_DEFAULT);
            switch(launcherType) {
                case INSTRUMENTS_SIMULATOR_LAUNCHER:
                    simulatorService = new InstrumentsSimulatorProcess(applicationBundle(), deviceType(), shell());
                    break;
                case VICTOR_3_0_SIMULATOR_LAUNCHER:
                    IosApplicationBundle iosApplicationBundle = applicationBundle();
                    if(!iosApplicationBundle.isExecutable()) {
                        throw new ConfigurationException("Application binary is not executable: " + iosApplicationBundle.pathToExecutable());
                    }
                    String sdkPath = sdk().path();
                    String simulatorBinaryPath = sdk().simulatorBinaryPath();
                    String applicationBinaryPath = iosApplicationBundle.pathToExecutable();
                    simulatorService = new VictorSimulatorProcess(sdkPath, simulatorBinaryPath, applicationBinaryPath, deviceType(), shell());
                    break;
                default:
                    simulatorService = new UserSimulatorProcess();
            }
        }
        return simulatorService;
    }
}
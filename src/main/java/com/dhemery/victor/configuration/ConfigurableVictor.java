package com.dhemery.victor.configuration;

import com.dhemery.configuring.Configuration;
import com.dhemery.configuring.ConfigurationException;
import com.dhemery.configuring.MapBackedConfiguration;
import com.dhemery.core.Builder;
import com.dhemery.core.Lazily;
import com.dhemery.core.Lazy;
import com.dhemery.network.CodecEndpoint;
import com.dhemery.network.Endpoint;
import com.dhemery.network.SerializingEndpoint;
import com.dhemery.network.URLEndpoint;
import com.dhemery.os.ProcessBuilderShell;
import com.dhemery.os.PublishingShell;
import com.dhemery.os.Shell;
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

import java.util.Arrays;
import java.util.List;

/**
 * A Victor environment configured according to specified configuration options.
 */
public class ConfigurableVictor implements Victor {
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
     * The default value for the {@link #FRANK_HOST} option.
     */
    public static final String DEFAULT_FRANK_HOST = "localhost";

    /**
     * The default value for the {@link #FRANK_PORT} option.
     */
    public static final String DEFAULT_FRANK_PORT = "37265";

    /**
     * The default value for the {@link #SIMULATOR_LAUNCHER} option.
     */
    public static final String DEFAULT_SIMULATOR_LAUNCHER = "instruments";

    /**
     * The type of device to simulate.
     * See your iOS Simulator's <em>Hardware > Device</em> menu for possible values.
     */
    public static final String DEVICE_TYPE = "victor.simulator.device.type";

    /**
     * The protocol that Victor uses to communicate with the Frank server.
     */
    public static final String FRANK_ENDPOINT_PROTOCOL = "http";

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
     * <p>Specifies the launcher for starting and stopping the simulator.</p>
     * <p>If the value is <strong>instruments</strong>,
     * Victor will launch the iOS simulator application
     * via <em>instruments</em>.
     * </p>
     * <p>If the value is <strong>obsolete-victor</strong>,
     * Victor will launch the simulator directly
     * using command line commands.
     * <strong>WARNING:</strong>
     * The <em>obsolete-victor</em> launcher requires Xcode 4.
     * It does not work with Xcode 5.
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
    public static final String SIMULATOR_LAUNCHER = "victor.simulator.launcher";
    public static final String INSTRUMENTS_SIMULATOR_LAUNCHER = "instruments";
    public static final String OBSOLETE_VICTOR_SIMULATOR_LAUNCHER = "obsolete-victor";

    private final Lazy<IosApplication> application = Lazily.build(theApplication());
    private final Lazy<IosApplicationBundle> applicationBundle = Lazily.build(theApplicationBundle());
    private final Lazy<IosDevice> device = Lazily.build(theDevice());
    private final Lazy<String> deviceType = Lazily.build(theDeviceType());
    private final Lazy<Frank> frank = Lazily.build(theFrank());
    private final Lazy<IosSdk> sdk = Lazily.build(theSdk());
    private final Lazy<Service> simulator = Lazily.build(theSimulator());

    private final Configuration configuration;
    private final Publisher publisher;
    private final Shell shell;

    /**
     * Create a Victor factory with the specified configuration options.
     * @param configuration the configuration options for Victor.
     */
    public ConfigurableVictor(Configuration configuration) {
        this(configuration, defaultPublisher());
    }

    public ConfigurableVictor(Configuration configuration, Publisher publisher) {
        this.configuration = new MapBackedConfiguration(configuration);
        this.publisher = publisher;
        shell = shellPublishedBy(publisher);
    }

    /**
     * An application bundle inspector for the configured application.
     */
    @Override
    public IosApplicationBundle applicationBundle() {
        return applicationBundle.get();
    }

    /**
     * A driver for the configured application.
     */
    @Override
    public IosApplication application() {
        return application.get();
    }

    /**
     * A driver for the configured device.
     */
    @Override
    public IosDevice device() {
        return device.get();
    }

    /**
     * The Frank agent used by the application and by view agents.
     */
    public Frank frank() {
        return frank.get();
    }

    /**
     * The iOS SDK used to run the device and application.
     */
    @Override
    public IosSdk sdk() {
        return sdk.get();
    }

    private static Publisher defaultPublisher() {
        return new Publisher() {
            @Override
            public void publish(Object publication) {}
        };
    }

    private String option(String property, String defaultValue) {
        if(!configuration.defines(property)) {
            configuration.define(property, defaultValue);
        }
        return configuration.option(property);
    }

    private Builder<IosApplication> theApplication() {
        return new Builder<IosApplication>() {
            @Override
            public IosApplication build() {
                return new FrankApplication(frank());
            }
        };
    }

    private Builder<IosApplicationBundle> theApplicationBundle() {
        return new Builder<IosApplicationBundle>() {
            @Override
            public IosApplicationBundle build() {
                return new FileSystemIosApplicationBundle(configuration.requiredOption(APPLICATION_BUNDLE_PATH), shell);
            }
        };
    }

    private Builder<IosDevice> theDevice() {
        return new Builder<IosDevice>() {
            @Override
            public IosDevice build() {
                SimulatorApplication simulatorApplication = new SimulatorApplication(shell);
                return new SimulatedIosDevice(deviceType.get(), simulatorApplication, simulator.get());
            }
        };
    }

    private Builder<String> theDeviceType() {
        return new Builder<String>() {
            @Override
            public String build() {
                List<String> deviceTypes = applicationBundle().deviceTypes();
                String defaultDeviceType = deviceTypes.size() == 1 ? deviceTypes.get(0) : DEFAULT_DEVICE_TYPE;
                return option(DEVICE_TYPE, defaultDeviceType);
            }
        };
    }

    private Builder<Frank> theFrank() {
        return new Builder<Frank>() {
            @Override
            public Frank build() {
                String host = option(FRANK_HOST, DEFAULT_FRANK_HOST);
                int port = Integer.parseInt(option(FRANK_PORT, DEFAULT_FRANK_PORT));
                Endpoint endpoint = new URLEndpoint(FRANK_ENDPOINT_PROTOCOL, host, port);
                Codec codec = new FranklyJsonCodec();
                SerializingEndpoint franklyEndpoint = new CodecEndpoint(endpoint, codec);
                return new PublishingFrank(publisher, new FranklyFrank(franklyEndpoint));
            }
        };
    }

    private Builder<IosSdk> theSdk() {
        return new Builder<IosSdk>() {
            @Override
            public IosSdk build() {
                List<Builder<String>> prioritizedSdkNames = Arrays.asList(
                        new Builder<String>() {
                            @Override
                            public String build() {
                                if(!configuration.defines(SDK_VERSION)) return null;
                                return "iphonesimulator" + configuration.option(SDK_VERSION);
                            }
                        },
                        new Builder<String>() {

                            @Override
                            public String build() {
                                return applicationBundle().targetSdkCanonicalName();
                            }
                        },
                        new Builder<String>() {
                            @Override
                            public String build() {
                                return "iphonesimulator";
                            }
                        }
                );

                for(Builder<String> builder : prioritizedSdkNames) {
                    String sdkName = builder.build();
                    if(sdkName == null) continue;
                    IosSdk sdk = new FileSystemIosSdk(sdkName, shell);
                    if(sdk.isInstalled()) return sdk;
                }
                throw new ConfigurationException("No iphonesimulator SDK installed on this computer");
            }
        };
    }

    private static Shell shellPublishedBy(Publisher publisher) {
        return new PublishingShell(publisher, new ProcessBuilderShell());
    }


    private Builder<Service> theSimulator() {
        return new Builder<Service>() {
            @Override
            public Service build() {
                String launcherType = option(SIMULATOR_LAUNCHER, DEFAULT_SIMULATOR_LAUNCHER);
                switch(launcherType) {
                    case INSTRUMENTS_SIMULATOR_LAUNCHER:
                        return new InstrumentsSimulatorProcess(applicationBundle(), deviceType.get(), shell);
                    case OBSOLETE_VICTOR_SIMULATOR_LAUNCHER:
                        IosApplicationBundle iosApplicationBundle = applicationBundle();
                        if(!iosApplicationBundle.isExecutable()) {
                            throw new ConfigurationException("Application binary is not executable: " + iosApplicationBundle.pathToExecutable());
                        }
                        String sdkPath = sdk().path();
                        String simulatorBinaryPath = sdk().simulatorBinaryPath();
                        String applicationBinaryPath = iosApplicationBundle.pathToExecutable();
                        return new VictorSimulatorProcess(sdkPath, simulatorBinaryPath, applicationBinaryPath, deviceType.get(), shell);
                    default:
                        return new UserSimulatorProcess();
                }
            }
        };
    }

}
package com.dhemery.victor;

import com.dhemery.configuring.Configuration;
import com.dhemery.configuring.ConfigurationException;
import com.dhemery.creating.Builder;
import com.dhemery.creating.Lazily;
import com.dhemery.creating.Lazy;
import com.dhemery.network.*;
import com.dhemery.os.*;
import com.dhemery.publishing.Channel;
import com.dhemery.publishing.Distributor;
import com.dhemery.publishing.MethodSubscriptionChannel;
import com.dhemery.serializing.Codec;
import com.dhemery.victor.device.*;
import com.dhemery.victor.discovery.IosApplicationBundle;
import com.dhemery.victor.discovery.IosSdk;
import com.dhemery.victor.discovery.SdkInspector;
import com.dhemery.victor.discovery.SdkItem;
import com.dhemery.victor.frank.Frank;
import com.dhemery.victor.frank.FrankApplication;
import com.dhemery.victor.frank.FranklyFrank;
import com.dhemery.victor.frank.PublishingFrank;
import com.dhemery.victor.frank.frankly.FranklyJsonCodec;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Victor environment configured according to specified configuration options.
 */
public class Victor {
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
     * The default value for the {@link #SIMULATOR_PROCESS_OWNER} option.
     */
    public static final String DEFAULT_SIMULATOR_PROCESS_OWNER = "victor";

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

    private final Lazy<IosApplication> application = Lazily.build(theApplication());
    private final Lazy<IosApplicationBundle> applicationBundle = Lazily.build(theApplicationBundle());
    private final Channel publisher = new MethodSubscriptionChannel();
    private final Lazy<IosDevice> device = Lazily.build(theDevice());
    private final Lazy<String> deviceType = Lazily.build(theDeviceType());
    private final Lazy<Frank> frank = Lazily.build(theFrank());
    private final Lazy<IosSdk> sdk = Lazily.build(theSdk());
    private final Lazy<SdkInspector> sdkInspector = Lazily.build(theSdkInspector());
    private final Lazy<Shell> shell = Lazily.build(theShell());
    private final Lazy<Service> simulator = Lazily.build(theSimulator());

    private final Configuration configuration;

    /**
     * Create a Victor factory with the specified configuration options.
     * @param configuration the configuration options for Victor.
     */
    public Victor(Configuration configuration) {
        this.configuration = new Configuration(configuration);
    }

    /**
     * An application bundle inspector for the configured application.
     */
    public IosApplicationBundle applicationBundle() {
        return applicationBundle.get();
    }

    /**
     * A driver for the configured application.
     */
    public IosApplication application() {
        return application.get();
    }

    /**
     * A driver for the configured device.
     */
    public IosDevice device() {
        return device.get();
    }

    /**
     * A distributor through which to subscribe to events published by Victor's creations.
     */
    public Distributor events() {
        return publisher;
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
    public IosSdk sdk() {
        return sdk.get();
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
                return new IosApplicationBundle(configuration.requiredOption(APPLICATION_BUNDLE_PATH), shell.get());
            }
        };
    }

    private Builder<IosDevice> theDevice() {
        return new Builder<IosDevice>() {
            @Override
            public IosDevice build() {
                SimulatorApplication simulatorApplication = new SimulatorApplication(shell.get());
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
                ResourceFactory resources = new URLResourceFactory();
                ResourceFactory publishingResources = new PublishingResourceFactory(publisher, resources);
                Endpoint endpoint = new ResourceFactoryBasedEndpoint(FRANK_ENDPOINT_PROTOCOL, host, port, publishingResources);
                Codec codec = new FranklyJsonCodec();
                return new PublishingFrank(publisher, new FranklyFrank(endpoint, codec));
            }
        };
    }

    private Builder<SdkInspector> theSdkInspector() {
        return new Builder<SdkInspector>() {
            @Override
            public SdkInspector build() {
                return new SdkInspector() {
                    Map<SdkItem,String> items = new HashMap<SdkItem,String>();
                    @Override
                    public String get(String canonicalName, String itemName) {
                        SdkItem item = new SdkItem(canonicalName, itemName);
                        if(!items.containsKey(item)) {
                            String value = shell.get().command("SDK Inspector", "xcodebuild")
                                .withArguments("-sdk", canonicalName)
                                .withArguments("-version", itemName)
                                .build().run().output();
                            items.put(item, value);
                        }
                        return items.get(item);
                    }
                };
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
                                return String.valueOf(applicationBundle().sdkCanonicalName());
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
                    IosSdk sdk = new IosSdk(sdkName, sdkInspector.get());
                    if(sdk.isInstalled()) return sdk;
                }
                throw new ConfigurationException("No iphonesimulator SDK installed on this computer");
            }
        };
    }

    private Builder<Shell> theShell() {
        return new Builder<Shell>() {
            @Override
            public Shell build() {
                OSCommandFactory<RunnableCommand> commandFactory = new RuntimeCommandFactory();
                PublishingCommandFactory publishingCommandFactory = new PublishingCommandFactory(publisher, commandFactory);
                return new FactoryBasedShell(publishingCommandFactory);
            }
        };
    }

    private Builder<Service> theSimulator() {
        return new Builder<Service>() {
            @Override
            public Service build() {
                String processOwner = option(SIMULATOR_PROCESS_OWNER, DEFAULT_SIMULATOR_PROCESS_OWNER);
                boolean victorOwnsSimulator = processOwner.equals(DEFAULT_SIMULATOR_PROCESS_OWNER);
                if (victorOwnsSimulator) {
                    IosApplicationBundle iosApplicationBundle = applicationBundle();
                    if(!iosApplicationBundle.isExecutable()) {
                        throw new ConfigurationException("Application binary is not executable: " + iosApplicationBundle.pathToExecutable());
                    }
                    String sdkPath = sdk().path();
                    String simulatorBinaryPath = sdk().simulatorBinaryPath();
                    String applicationBinaryPath = iosApplicationBundle.pathToExecutable();
                    return new VictorSimulatorProcess(sdkPath, simulatorBinaryPath, applicationBinaryPath, deviceType.get(), shell.get());
                }
                return new UserSimulatorProcess();
            }
        };
    }
}
package com.dhemery.victor;

import com.dhemery.builder.Builder;
import com.dhemery.builder.Lazily;
import com.dhemery.builder.Lazy;
import com.dhemery.configuration.Configuration;
import com.dhemery.configuration.ConfigurationException;
import com.dhemery.network.*;
import com.dhemery.os.FactoryBasedShell;
import com.dhemery.os.PublishingCommandFactory;
import com.dhemery.os.Shell;
import com.dhemery.publishing.DistributingPublisher;
import com.dhemery.publishing.Distributor;
import com.dhemery.victor.device.*;
import com.dhemery.victor.discovery.IosApplicationBundle;
import com.dhemery.victor.discovery.IosSdk;
import com.dhemery.victor.discovery.SdkInspector;
import com.dhemery.victor.discovery.SdkItem;
import com.dhemery.victor.frank.*;
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

    private final Lazy<IosApplication> application = Lazily.from(applicationBuilder());
    private final Lazy<IosApplicationBundle> applicationBundle = Lazily.from(applicationBundleBuilder());
    private final DistributingPublisher publisher = new DistributingPublisher();
    private final Lazy<IosDevice> device = Lazily.from(deviceBuilder());
    private final Lazy<String> deviceType = Lazily.from(deviceTypeBuilder());
    private final Lazy<Frank> frank = Lazily.from(frankBuilder());
    private final Lazy<IosSdk> sdk = Lazily.from(sdkBuilder());
    private final Lazy<SdkInspector> sdkInspector = Lazily.from(sdkInspectorBuilder());
    private final Lazy<Shell> shell = Lazily.from(shellBuilder());
    private final Lazy<Service> simulator = Lazily.from(simulatorBuilder());
    private final Lazy<IosViewAgent> viewAgent = Lazily.from(viewAgentBuilder());
    private final Lazy<IosViewFactory> viewFactory = Lazily.from(viewFactoryBuilder());

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

    /**
     * The view agent that Victor's view factory uses to create view drivers.
     */
    public IosViewAgent viewAgent() {
        return viewAgent.get();
    }


    /**
     * The factory that creates views backed by Victor's view agent.
     */
    public IosViewFactory viewFactory() {
        return viewFactory.get();
    }

    private Builder<IosApplication> applicationBuilder() {
        return new Builder<IosApplication>() {
            @Override
            public IosApplication build() {
                return new FrankApplication(frank());
            }
        };
    }

    private Builder<IosApplicationBundle> applicationBundleBuilder() {
        return new Builder<IosApplicationBundle>() {
            @Override
            public IosApplicationBundle build() {
                return new IosApplicationBundle(configuration.requiredOption(APPLICATION_BUNDLE_PATH), shell.get());
            }
        };
    }

    private Builder<IosDevice> deviceBuilder() {
        return new Builder<IosDevice>() {
            @Override
            public IosDevice build() {
                SimulatorApplication simulatorApplication = new SimulatorApplication(shell.get());
                return new SimulatedIosDevice(deviceType.get(), simulatorApplication, simulator.get());
            }
        };
    }

    private Builder<String> deviceTypeBuilder() {
        return new Builder<String>() {
            @Override
            public String build() {
                List<String> deviceTypes = applicationBundle().deviceTypes();
                String defaultDeviceType = deviceTypes.size() == 1 ? deviceTypes.get(0) : DEFAULT_DEVICE_TYPE;
                return option(DEVICE_TYPE, defaultDeviceType);
            }
        };
    }

    private Builder<Frank> frankBuilder() {
        return new Builder<Frank>() {
            @Override
            public Frank build() {
                String host = option(FRANK_HOST, DEFAULT_FRANK_HOST);
                int port = Integer.parseInt(option(FRANK_PORT, DEFAULT_FRANK_PORT));
                Router router = new URLResourceRouter(FRANK_ENDPOINT_PROTOCOL);
                Endpoint endpoint = new RoutedEndpoint(router, host, port);
                Endpoint publishingEndpoint = new PublishingEndpoint(publisher, endpoint);
                Codec codec = new FranklyJsonCodec();
                return new PublishingFrank(publisher, new FranklyFrank(publishingEndpoint, codec));
            }
        };
    }

    private String option(String property, String defaultValue) {
        if(!configuration.defines(property)) {
            configuration.set(property, defaultValue);
        }
        return configuration.option(property);
    }

    private Builder<SdkInspector> sdkInspectorBuilder() {
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

    private Builder<IosSdk> sdkBuilder() {
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

    private Builder<Shell> shellBuilder() {
        return new Builder<Shell>() {
            @Override
            public Shell build() {
                PublishingCommandFactory commandFactory = new PublishingCommandFactory(publisher);
                return new FactoryBasedShell(commandFactory);
            }
        };
    }

    private Builder<Service> simulatorBuilder() {
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

    private Builder<IosViewAgent> viewAgentBuilder() {
        return new Builder<IosViewAgent>() {
            @Override
            public IosViewAgent build() {
                return new FrankViewAgent(frank());
            }
        };
    }

    private Builder<IosViewFactory> viewFactoryBuilder() {
        return new Builder<IosViewFactory>() {
            @Override
            public IosViewFactory build() {
                return new AgentBackedViewFactory(viewAgent());
            }
        };
    }
}
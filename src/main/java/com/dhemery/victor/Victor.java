package com.dhemery.victor;

import com.dhemery.configuration.Configuration;
import com.dhemery.configuration.ConfigurationException;
import com.dhemery.configuration.SingleSourceMappedCache;
import com.dhemery.network.*;
import com.dhemery.os.FactoryBasedShell;
import com.dhemery.os.Shell;
import com.dhemery.os.publishing.PublishingCommandFactory;
import com.dhemery.osx.AppleScriptShell;
import com.dhemery.publishing.Distributor;
import com.dhemery.publishing.EventBusPublisher;
import com.dhemery.victor.device.*;
import com.dhemery.victor.discovery.IosApplicationBundle;
import com.dhemery.victor.discovery.IosSdk;
import com.dhemery.victor.discovery.SdkItemKey;
import com.dhemery.victor.discovery.SdkItemSource;
import com.dhemery.victor.frank.Frank;
import com.dhemery.victor.frank.FrankApplication;
import com.dhemery.victor.frank.FrankViewAgent;
import com.dhemery.victor.frank.publishing.PublishingFrank;
import com.dhemery.victor.frankly.FranklyFrank;
import com.dhemery.victor.frankly.FranklyJsonCodec;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.eventbus.EventBus;

import java.util.List;

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

    private final Supplier<IosApplication> application = Suppliers.memoize(applicationSupplier());
    private final Supplier<IosApplicationBundle> applicationBundle = Suppliers.memoize(applicationBundleSupplier());
    private final Supplier<IosDevice> device = Suppliers.memoize(deviceSupplier());
    private final Supplier<String> deviceType = Suppliers.memoize(deviceTypeSupplier());
    private final Supplier<Frank> frank = Suppliers.memoize(frankSupplier());
    private final Supplier<EventBusPublisher> publisher = Suppliers.memoize(publisherSupplier());
    private final Supplier<IosSdk> sdk = Suppliers.memoize(sdkSupplier());
    private final Supplier<Shell> shell = Suppliers.memoize(shellSupplier());
    private final Supplier<Service> simulator = Suppliers.memoize(simulatorSupplier());
    private final Supplier<IosViewAgent> viewAgent = Suppliers.memoize(viewAgentSupplier());
    private final Supplier<IosViewFactory> viewFactory = Suppliers.memoize(viewFactorySupplier());

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
    public Distributor<Object> events() {
        return publisher.get();
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

    private Supplier<IosApplication> applicationSupplier() {
        return new Supplier<IosApplication>() {
            @Override
            public IosApplication get() {
                return new FrankApplication(frank());
            }
        };
    }

    private Supplier<IosApplicationBundle> applicationBundleSupplier() {
        return new Supplier<IosApplicationBundle>() {
            @Override
            public IosApplicationBundle get() {
                return new IosApplicationBundle(configuration.requiredOption(APPLICATION_BUNDLE_PATH), shell.get());
            }
        };
    }

    private Supplier<IosDevice> deviceSupplier() {
        return new Supplier<IosDevice>() {
            @Override
            public IosDevice get() {
                AppleScriptShell appleScriptShell = new AppleScriptShell(shell.get());
                SimulatorApplication simulatorApplication = new SimulatorApplication(appleScriptShell);
                return new SimulatedIosDevice(deviceType.get(), simulatorApplication, simulator.get());
            }
        };
    }

    private Supplier<String> deviceTypeSupplier() {
        return new Supplier<String>() {
            @Override
            public String get() {
                List<String> deviceTypes = applicationBundle().deviceTypes();
                String defaultDeviceType = deviceTypes.size() == 1 ? deviceTypes.get(0) : DEFAULT_DEVICE_TYPE;
                return option(DEVICE_TYPE, defaultDeviceType);
            }
        };
    }

    private Supplier<Frank> frankSupplier() {
        return new Supplier<Frank>() {
            @Override
            public Frank get() {
                String host = option(FRANK_HOST, DEFAULT_FRANK_HOST);
                int port = Integer.parseInt(option(FRANK_PORT, DEFAULT_FRANK_PORT));
                Router router = new URLResourceRouter(FRANK_ENDPOINT_PROTOCOL);
                Endpoint endpoint = new RoutedEndpoint(router, host, port);
                Codec codec = new FranklyJsonCodec();
                return new PublishingFrank(publisher.get(), new FranklyFrank(endpoint, codec));
            }
        };
    }

    private String option(String property, String defaultValue) {
        if(!configuration.defines(property)) {
            configuration.set(property, defaultValue);
        }
        return configuration.option(property);
    }

    private Supplier<EventBusPublisher> publisherSupplier() {
        return new Supplier<EventBusPublisher>() {
            @Override
            public EventBusPublisher get() {
                return new EventBusPublisher(new EventBus());
            }
        };
    }

    private Supplier<IosSdk> sdkSupplier() {
        return new Supplier<IosSdk>() {
            @Override
            public IosSdk get() {
                SdkItemSource sdkItemSource = new SdkItemSource(shell.get());
                SingleSourceMappedCache<SdkItemKey,String> sdkInfoCache = new SingleSourceMappedCache<SdkItemKey, String>(sdkItemSource);
                if(configuration.defines(SDK_VERSION)) {
                    IosSdk userPreferredSdk = IosSdk.withVersion(sdkInfoCache, configuration.option(SDK_VERSION));
                    if (userPreferredSdk.isInstalled()) return userPreferredSdk;
                }

                String canonicalName = applicationBundle().sdkCanonicalName();
                if(canonicalName != null) {
                    IosSdk bundlePreferredSdk = IosSdk.withCanonicalName(sdkInfoCache, canonicalName);
                    if (bundlePreferredSdk.isInstalled()) return bundlePreferredSdk;
                }

                IosSdk newestInstalledSdk = IosSdk.newest(sdkInfoCache);
                if (newestInstalledSdk.isInstalled()) return newestInstalledSdk;

                throw new ConfigurationException("No iphonesimulator SDK installed on this computer");
            }
        };
    }

    private Supplier<Shell> shellSupplier() {
        return new Supplier<Shell>() {
            @Override
            public Shell get() {
                PublishingCommandFactory commandFactory = new PublishingCommandFactory(publisher.get());
                return new FactoryBasedShell(commandFactory);
            }
        };
    }

    private Supplier<Service> simulatorSupplier() {
        return new Supplier<Service>() {
            @Override
            public Service get() {
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

    private Supplier<IosViewAgent> viewAgentSupplier() {
        return new Supplier<IosViewAgent>() {
            @Override
            public IosViewAgent get() {
                return new FrankViewAgent(frank());
            }
        };
    }

    private Supplier<IosViewFactory> viewFactorySupplier() {
        return new Supplier<IosViewFactory>() {
            @Override
            public IosViewFactory get() {
                return new AgentBackedViewFactory(viewAgent());
            }
        };
    }
}

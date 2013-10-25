package com.dhemery.victor.build;

import com.dhemery.core.Builder;
import com.dhemery.network.CodecEndpoint;
import com.dhemery.network.Endpoint;
import com.dhemery.network.SerializingEndpoint;
import com.dhemery.network.URLEndpoint;
import com.dhemery.os.ProcessBuilderShell;
import com.dhemery.os.PublishingShell;
import com.dhemery.os.Shell;
import com.dhemery.osx.OsxApplication;
import com.dhemery.publishing.MethodSubscriptionChannel;
import com.dhemery.publishing.Publisher;
import com.dhemery.serializing.Codec;
import com.dhemery.victor.*;
import com.dhemery.victor.device.InstrumentsSimulatorProcess;
import com.dhemery.victor.device.Service;
import com.dhemery.victor.device.SimulatedIosDevice;
import com.dhemery.victor.device.SimulatorApplication;
import com.dhemery.victor.discovery.FileSystemIosApplicationBundle;
import com.dhemery.victor.discovery.FileSystemIosSdk;
import com.dhemery.victor.frank.Frank;
import com.dhemery.victor.frank.FrankApplication;
import com.dhemery.victor.frank.FranklyFrank;
import com.dhemery.victor.frank.frankly.FranklyJsonCodec;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Build a Victor environment using default values and user-supplied values.
 */
public class VictorBuilder implements Builder<Victor> {
    private static final String DEFAULT_DEVICE_TYPE = "iPhone";
    public static final String DEFAULT_FRANK_URL = "http://localhost:37265";
    private final String applicationBundlePath;
    private IosApplication application;
    private IosApplicationBundle applicationBundle;
    private Codec franklyCodec;
    private IosDevice device;
    private String deviceType;
    private Frank frank;
    private Endpoint frankEndpoint;
    private SerializingEndpoint franklyEndpoint;
    private URL frankUrl;
    private Publisher publisher;
    private IosSdk sdk;
    private String sdkCanonicalName;
    private Shell shell;
    private OsxApplication simulatorApplication;
    private Service simulatorService;

    private VictorBuilder(String bundlePath){
        this.applicationBundlePath = bundlePath;
    }

    public static VictorBuilder victorForApplicationBundle(String bundlePath) {
        return new VictorBuilder(bundlePath);
    }

    @Override
    public Victor build() {
        return new DefaultVictor(application(), applicationBundle(), device(), sdk());
    }

    /**
     * Supply an application to interact with.
     * @return this builder
     */
    public VictorBuilder withApplication(IosApplication application) {
        this.application = application;
        return this;
    }

    /**
     * Supply an application bundle
     * to describe the application.
     * @return this builder
     */
    public VictorBuilder withApplicationBundle(IosApplicationBundle applicationBundle) {
        this.applicationBundle = applicationBundle;
        return this;
    }

    /**
     * Supply an iOS device to simulate.
     * @return this builder
     */
    public VictorBuilder withDevice(IosDevice device) {
        this.device = device;
        return this;
    }

    /**
     * Specify a type of device to simulate.
     * @return this builder
     */
    public VictorBuilder withDeviceType(String deviceType){
        this.deviceType = deviceType;
        return this;
    }

    /**
     * Supply a Frank driver
     * to communicate with the application
     * through the Frank server.
     * @return this builder
     */
    public VictorBuilder withFrank(Frank frank) {
        this.frank = frank;
        return this;
    }

    /**
     * Supply an endpoint to interact with a Frank server via HTTP.
     * @return this builder
     */
    public VictorBuilder withFrankEndpoint(Endpoint frankEndpoint) {
        this.frankEndpoint = frankEndpoint;
        return this;
    }

    /**
     * Supply the URL of the Frank server.
     * @return this builder
     */
    public VictorBuilder withFrankUrl(URL frankUrl) {
        this.frankUrl = frankUrl;
        return this;
    }

    /**
     * Supply a codec that can serialize and deserialize Frankly messages.
     * The codec must be able to:
     * <ul>
     * <li>Serialize each {@code FranklyFrank} message object
     * into the corresponding Frankly wire protocol format.</li>
     * <li>Deserialize each response from its Frankly wire protocol format
     * @return this builder
     */
    public VictorBuilder withFranklyCodec(Codec franklyCodec) {
        this.franklyCodec = franklyCodec;
        return this;
    }

    /**
     * Supply a serializing endpoint
     * that can send and receive {@link FranklyFrank} messages.
     * The serializing endpoint must be able to:
     * <ul>
     * <li>Serialize each {@code FranklyFrank} message object
     * into the corresponding Frankly wire protocol format.</li>
     * <li>Send each serialized message to the Frank server via HTTP.</li>
     * <li>Receive each serialized response from the Frank server via HTTP.</li>
     * <li>Deserialize each response from its Frankly wire protocol format
     * into the corresponding {@code FranklyFrank} response object.</li>
     * @return this builder
     */
    public VictorBuilder withFranklyEndpoint(SerializingEndpoint franklyEndpoint) {
        this.franklyEndpoint = franklyEndpoint;
        return this;
    }

    /**
     * Supply a publisher to publish events.
     * @return this builder
     */
    public VictorBuilder withPublisher(Publisher publisher) {
        this.publisher = publisher;
        return this;
    }

    /**
     * Supply an SDK.
     * @return this builder
     */
    public VictorBuilder withSdk(IosSdk sdk) {
        this.sdk = sdk;
        return this;
    }

    /**
     * Specify the canonical name of the SDK to use to launc the simulator.
     * @return this builder
     */
    public VictorBuilder withSdkCanonicalName(String sdkCanonicalName) {
        this.sdkCanonicalName = sdkCanonicalName;
        return this;
    }

    /**
     * Supply a shell to execute command line commands.
     * @return this builder
     */
    public VictorBuilder withShell(Shell shell) {
        this.shell = shell;
        return this;
    }

    /**
     * Supply a driver to interact with a running iOS simulator application.
     * @return this builder
     */
    public VictorBuilder withSimulatorApplication(OsxApplication simulatorApplication){
        this.simulatorApplication = simulatorApplication;
        return this;
    }

    /**
     * Supply a service to start and stop the simulator.
     * @return this builder
     */
    public VictorBuilder withSimulatorService(Service simulatorService) {
        this.simulatorService = simulatorService;
        return this;
    }

    private IosApplication application() {
        if(application == null) application = new FrankApplication(frank());
        return application;
    }

    private IosApplicationBundle applicationBundle() {
        if(applicationBundle == null) applicationBundle = new FileSystemIosApplicationBundle(applicationBundlePath, shell());
        return applicationBundle;
    }

    private String bundlePreferredDeviceType() {
        List<String> deviceTypes = applicationBundle().deviceTypes();
        if(deviceTypes.size() == 1) return deviceTypes.get(0);
        return DEFAULT_DEVICE_TYPE;
    }

    private IosDevice device() {
        if(device == null) device = new SimulatedIosDevice(deviceType(), simulatorApplication(), simulatorService());
        return device;
    }

    private String deviceType() {
        if(deviceType == null) deviceType = bundlePreferredDeviceType();
        return deviceType;
    }

    private Frank frank() {
        if(frank == null) frank = new FranklyFrank(franklyEndpoint());
        return frank;
    }

    private SerializingEndpoint franklyEndpoint() {
        if(franklyEndpoint == null) franklyEndpoint = new CodecEndpoint(frankEndpoint(), franklyCodec());
        return franklyEndpoint;
    }

    private Endpoint frankEndpoint() {
        if(frankEndpoint == null) {
            URL url = frankUrl();
            String protocol = url.getProtocol();
            String host = url.getHost();
            int port = url.getPort();
            frankEndpoint = new URLEndpoint(protocol, host, port);
        }
        return frankEndpoint;
    }

    private Codec franklyCodec() {
        if(franklyCodec == null) franklyCodec = new FranklyJsonCodec();
        return franklyCodec;
    }

    private URL frankUrl() {
        if(frankUrl == null) frankUrl = url(DEFAULT_FRANK_URL);
        return frankUrl;
    }

    private Publisher publisher() {
        if(publisher == null) publisher = new MethodSubscriptionChannel();
        return publisher;
    }

    private IosSdk sdk() {
        if(sdk == null) sdk = new FileSystemIosSdk(sdkCanonicalName(), shell());
        return sdk;
    }

    private String sdkCanonicalName() {
        if(sdkCanonicalName == null) sdkCanonicalName = applicationBundle().targetSdkCanonicalName();
        return sdkCanonicalName;
    }

    private Shell shell() {
        if(shell == null) shell = new PublishingShell(publisher(), new ProcessBuilderShell());
        return shell;
    }

    private OsxApplication simulatorApplication() {
        if(simulatorApplication == null) simulatorApplication = new SimulatorApplication(shell());
        return simulatorApplication;
    }

    private Service simulatorService() {
        if(simulatorService == null) simulatorService = new InstrumentsSimulatorProcess(applicationBundle(), deviceType(), shell());
        return simulatorService;
    }

    private static URL url(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}

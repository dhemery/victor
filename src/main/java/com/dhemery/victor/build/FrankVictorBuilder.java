package com.dhemery.victor.build;

import com.dhemery.core.Builder;
import com.dhemery.network.*;
import com.dhemery.os.*;
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

public class FrankVictorBuilder implements Builder<Victor> {
    private static final String DEFAULT_DEVICE_TYPE = "iPhone";
    public static final String DEFAULT_FRANK_URL = "http://localhost:37265";
    private final String applicationBundlePath;
    private IosApplication application;
    private IosApplicationBundle applicationBundle;
    private Codec codec;
    private SerializingEndpoint codecEndpoint;
    private OSCommandFactory<RunnableCommand> commandFactory;
    private IosDevice device;
    private String deviceType;
    private Frank frank;
    private Endpoint frankEndpoint;
    private URL frankUrl;
    private Publisher publisher;
    private ResourceFactory resourceFactory;
    private OSCommandFactory<RunnableCommand> runtimeCommandFactory;
    private IosSdk sdk;
    private String sdkCanonicalName;
    private Shell shell;
    private OsxApplication simulatorApplication;
    private Service simulatorProcess;

    private FrankVictorBuilder(String bundlePath){
        this.applicationBundlePath = bundlePath;
    }

    public static FrankVictorBuilder victor(String bundlePath) {
        return new FrankVictorBuilder(bundlePath);
    }

    @Override
    public Victor build() {
        return new DefaultVictor(application(), applicationBundle(), device(), sdk());
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

    private Codec codec() {
        if(codec == null) codec = new FranklyJsonCodec();
        return codec;
    }

    private SerializingEndpoint codecEndpoint() {
        if(codecEndpoint == null) codecEndpoint = new CodecEndpoint(frankEndpoint(), codec());
        return codecEndpoint;
    }

    private OSCommandFactory<RunnableCommand> commandFactory() {
        if(commandFactory == null) commandFactory = new PublishingCommandFactory(publisher(), runtimeCommandFactory());
        return commandFactory;
    }

    private IosDevice device() {
        if(device == null) device = new SimulatedIosDevice(deviceType(), simulatorApplication(), simulatorProcess());
        return null;
    }

    private String deviceType() {
        if(deviceType == null) deviceType = bundlePreferredDeviceType();
        return deviceType;
    }

    private Frank frank() {
        if(frank == null) frank = new FranklyFrank(codecEndpoint());
        return frank;
    }

    private Endpoint frankEndpoint() {
        if(frankEndpoint == null) {
            URL url = frankUrl();
            String protocol = url.getProtocol();
            String host = url.getHost();
            int port = url.getPort();
            frankEndpoint = new ResourceFactoryBasedEndpoint(protocol, host, port, resourceFactory());
        }
        return frankEndpoint;
    }

    private URL frankUrl() {
        if(frankUrl == null) frankUrl = url(DEFAULT_FRANK_URL);
        return frankUrl;
    }

    private Publisher publisher() {
        if(publisher == null) publisher = new MethodSubscriptionChannel();
        return publisher;
    }

    private ResourceFactory resourceFactory() {
        if(resourceFactory == null) resourceFactory  = new URLResourceFactory();
        return resourceFactory;
    }

    private OSCommandFactory<RunnableCommand> runtimeCommandFactory() {
        if(runtimeCommandFactory == null) runtimeCommandFactory = new RuntimeCommandFactory();
        return runtimeCommandFactory;
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
        if(shell == null) shell = new FactoryBasedShell(commandFactory());
        return shell;
    }

    private OsxApplication simulatorApplication() {
        if(simulatorApplication == null) simulatorApplication = new SimulatorApplication(shell());
        return simulatorApplication;
    }

    private Service simulatorProcess() {
        if(simulatorProcess == null) simulatorProcess = new InstrumentsSimulatorProcess(applicationBundle(), deviceType(), shell());
        return simulatorProcess;
    }

    private static URL url(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}

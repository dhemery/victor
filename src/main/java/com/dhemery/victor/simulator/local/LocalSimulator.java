package com.dhemery.victor.simulator.local;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dhemery.victor.simulator.Simulator;

/**
 * An iOS simulator running on this computer.
 * @author Dale Emery
 *
 */
public class LocalSimulator implements Simulator {
    private static final String RUN_HEADLESS = "-RegisterForSystemEvents";
    private static final String DEVELOPER_ROOT = "/Applications/Xcode.app/Contents/Developer";
    private static final String SIMULATOR_PATH = String.format("%s%s", DEVELOPER_ROOT, "/Platforms/iPhoneSimulator.platform/Developer/Applications/iPhone Simulator.app/Contents/MacOS/iPhone Simulator");
    private static final String SDK_ROOT_TEMPLATE=String.format("%s%s", DEVELOPER_ROOT, "/Platforms/iPhoneSimulator.platform/Developer/SDKs/iPhoneSimulator%s.sdk");
    private Process simulatorProcess;
    private Boolean runningHeadless;

    @Override
    public void launch(String applicationPath, String sdkVersion, Boolean headless) {
        String applicationAbsolutePath = new File(applicationPath).getAbsolutePath();
        String sdkRoot = String.format(SDK_ROOT_TEMPLATE, sdkVersion);
        String simulatorCommand;
        List<String> simulatorOptions = new ArrayList<String>();
        List<String> simulatorEnvironment = new ArrayList<String>();
        if(headless) {
            simulatorEnvironment.add(String.format("SDKDIR=%s", sdkRoot));
            simulatorEnvironment.add(String.format("DYLD_ROOT_PATH=%s", sdkRoot));
            simulatorEnvironment.add(String.format("IPHONE_SIMULATOR_ROOT=%s", sdkRoot));
            simulatorEnvironment.add("CFFIXED_USER_HOME=/tmp/dalestuffs");
            simulatorCommand = applicationAbsolutePath;
            simulatorOptions.add(RUN_HEADLESS);
        } else {
            simulatorOptions.addAll(Arrays.asList("-SimulateApplication", applicationAbsolutePath, /*"-SimulateDevice", "iPhone",*/ "-currentSDKRoot", sdkRoot));
            simulatorCommand = SIMULATOR_PATH;
        }
        runningHeadless = headless;
        simulatorProcess = new OSCommand(simulatorCommand, simulatorOptions, simulatorEnvironment).run();
    }

    @Override
    public void shutDown() {
        if(simulatorProcess == null) return;
        if(runningHeadless) {
            simulatorProcess.destroy();
        } else {
            touchMenuItem("iOS Simulator", "Quit iOS Simulator");
            waitForSimulatorToShutDown();
        }
        simulatorProcess = null;
    }

    @Override
    public void touchMenuItem(String menuName, String menuItemName) {
        new TouchMenuItemCommand(menuName, menuItemName).run();
    }

    private void waitForSimulatorToShutDown() {
        try {
            simulatorProcess.waitFor();
        } catch (InterruptedException e) {
            throw new SimulatorCommandException("Exception while waiting for simulator to shut down", e);
        }
    }
}

package com.dhemery.victor.simulator.local;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.dhemery.victor.simulator.Simulator;

/**
 * An iOS simulator running on this computer.
 * @author Dale Emery
 *
 */
public class LocalSimulator implements Simulator {
    private static final String RUN_HEADLESS = "-RegisterForSystemEvents";
    private static final String SDK_ROOT_TEMPLATE="/Applications/Xcode.app/Contents/Developer/Platforms/iPhoneSimulator.platform/Developer/SDKs/iPhoneSimulator%s.sdk";
    private final List<String> simulatorOptions = new ArrayList<String>();
    private final List<String> simulatorEnvironment = new ArrayList<String>();
    private Process simulatorProcess;

    public LocalSimulator(String sdkVersion, Boolean headless) {
        if(headless) simulatorOptions.add(RUN_HEADLESS);
        String sdkRoot = String.format(SDK_ROOT_TEMPLATE, sdkVersion);
        simulatorEnvironment.add(String.format("SDKDIR=%s", sdkRoot));
        simulatorEnvironment.add(String.format("DYLD_ROOT_PATH=%s", sdkRoot));
        simulatorEnvironment.add(String.format("IPHONE_SIMULATOR_ROOT=%s", sdkRoot));
    }

    @Override
	public void launch(String applicationPath) {
		String applicationAbsolutePath = new File(applicationPath).getAbsolutePath();
		simulatorProcess = new OSCommand(applicationAbsolutePath, simulatorOptions, simulatorEnvironment).run();
	}

	@Override
	public void shutDown() {
		if(simulatorProcess != null) {
			simulatorProcess.destroy();
		}
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

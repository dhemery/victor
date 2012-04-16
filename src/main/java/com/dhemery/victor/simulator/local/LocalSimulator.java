package com.dhemery.victor.simulator.local;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.dhemery.victor.simulator.Simulator;

/**
 * An iOS simulator running on this computer.
 * @author Dale Emery
 *
 */
public class LocalSimulator implements Simulator {
    private static final String SDK_ROOT="/Applications/Xcode.app/Contents/Developer/Platforms/iPhoneSimulator.platform/Developer/SDKs/iPhoneSimulator5.1.sdk";
    private static final List<String> SIMULATOR_ENVIRONMENT = Arrays.asList(
            String.format("SDKDIR=%s", SDK_ROOT),
            String.format("DYLD_ROOT_PATH=%s", SDK_ROOT),
            String.format("IPHONE_SIMULATOR_ROOT=%s", SDK_ROOT)
    );
    public static final List<String> HEADLESS = Arrays.asList("-RegisterForSystemEvents");
    private Process simulatorProcess;
	
	@Override
	public void launch(String applicationPath, String deviceType, String sdkRoot) {
		String applicationAbsolutePath = new File(applicationPath).getAbsolutePath();
		simulatorProcess = new OSCommand(applicationAbsolutePath, HEADLESS, SIMULATOR_ENVIRONMENT).run();
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

package com.dhemery.victor.device;

import com.dhemery.victor.IosDevice;
import com.dhemery.osx.OsxApplication;

/**
 * Interacts with a simulated iOS device through a various agents.
 */
public class SimulatedIosDevice implements IosDevice {
    private final String deviceType;
    private final OsxApplication simulatorApplication;
    private final Service simulatorProcess;

    /**
     * @param deviceType the type of device simulated by this driver
     * @param simulatorApplication a driver to interact with the simulator application
     * @param simulatorProcess a driver to start and stop the simulator
     */
    public SimulatedIosDevice(String deviceType, OsxApplication simulatorApplication, Service simulatorProcess) {
        this.deviceType = deviceType;
        this.simulatorApplication = simulatorApplication;
        this.simulatorProcess = simulatorProcess;
    }

    @Override public String type() { return deviceType; }

    @Override
    public void rotateLeft() {
        simulatorApplication.touchMenuItem("Hardware", "Rotate Left");
    }

    @Override
    public void rotateRight() {
        simulatorApplication.touchMenuItem("Hardware", "Rotate Right");
    }

    @Override
    public void saveScreenShot() {
        simulatorApplication.touchMenuItem("File", "Save Screen Shot");
    }

    @Override
    public void start() {
        simulatorProcess.start();
    }

    @Override
    public void stop() {
        simulatorProcess.stop();
    }

    @Override
    public String toString() {
        return String.format("the simulated %s device", type());
    }
}

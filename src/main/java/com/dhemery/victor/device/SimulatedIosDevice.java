package com.dhemery.victor.device;

import com.dhemery.osx.OsxApplication;
import com.dhemery.victor.Orientation;
import com.dhemery.victor.IosDevice;
import com.dhemery.victor.frank.Frank;

/**
 * Interacts with a simulated iOS device through a various agents.
 */
public class SimulatedIosDevice implements IosDevice {
    private final String deviceType;
    private final OsxApplication simulatorApplication;
    private final Service simulatorProcess;
    private final Frank frank;

    /**
     * @param deviceType the type of device simulated by this driver
     * @param simulatorApplication a driver to interact with the simulator application
     * @param simulatorProcess a driver to start and stop the simulator
     */
    public SimulatedIosDevice(String deviceType, OsxApplication simulatorApplication, Service simulatorProcess, Frank frank) {
        this.deviceType = deviceType;
        this.simulatorApplication = simulatorApplication;
        this.simulatorProcess = simulatorProcess;
        this.frank = frank;
    }

    @Override public String type() { return deviceType; }

    @Override
    public void orientIn(Orientation orientation) {
        frank.orientIn(orientation.toString().toLowerCase());
    }

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

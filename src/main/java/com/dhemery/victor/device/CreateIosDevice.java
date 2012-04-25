package com.dhemery.victor.device;

import com.dhemery.victor.IosDevice;

public class CreateIosDevice {
    public static IosDevice withCapabilities(IosDeviceCapabilities deviceCapabilities) {
        Simulator simulator = new LocalSimulator(deviceCapabilities.sdkRoot(), deviceCapabilities.simulatorBinaryPath(), deviceCapabilities.applicationBinaryPath());
        return new SimulatedIosDevice(simulator);
    }
}

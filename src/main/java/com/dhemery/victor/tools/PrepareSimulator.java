package com.dhemery.victor.tools;

import com.dhemery.victor.device.LocalSimulator;
import com.dhemery.victor.device.Simulator;
import com.dhemery.victor.xcode.Xcode;

/**
 * <p>
 * Launch and shut down the simulator.
 * <strong>Run this command before running tests with Victor.</strong>
 * </p>
 * <p>
 * Launching the simulator on its own (without an application)
 * creates some helper processes that are necessary for running applications.
 * If you launch an application in the simulator before these helper methods have been created,
 * the simulator chews up CPU time looking for resources that are not available.
 * </p>
 */
public class PrepareSimulator {
    public static void main(String[] args) throws InterruptedException {
        new PrepareSimulator().run();
    }

    public void run() throws InterruptedException {
        Simulator simulator = new LocalSimulator(new Xcode().simulatorBinaryPath());
        simulator.start();
        Thread.sleep(3000);
        simulator.stop();
    }
}

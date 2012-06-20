package com.dhemery.victor.tools;

import com.dhemery.victor.device.SimulatorAgent;
import com.dhemery.victor.device.local.VictorSimulatorAgent;
import com.dhemery.victor.discovery.IosSdk;

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
    public static final int SIMULATOR_PREPARATION_DELAY = 3000;

    public static void main(String... args) throws InterruptedException {
        new PrepareSimulator().run();
    }

    public void run() throws InterruptedException {
        SimulatorAgent simulator = new VictorSimulatorAgent(IosSdk.simulatorBinaryPath());
        simulator.start();
        Thread.sleep(SIMULATOR_PREPARATION_DELAY);
        simulator.stop();
    }
}

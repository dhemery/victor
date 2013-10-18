package com.dhemery.victor.device;

import com.dhemery.os.RunnableCommand;
import com.dhemery.os.Shell;
import com.dhemery.victor.discovery.IosApplicationBundle;

public class InstrumentsSimulatorProcess implements Service {
    private static final String APPLICATION_LAUNCH_SCRIPT = "/com/dhemery/osx/launch-app-using-instruments.sh";
    private final RunnableCommand launch;
    private final RunnableCommand stopSimulator;
    private final RunnableCommand stopSimulatedApplication;
    private final RunnableCommand stopInstruments;

    public InstrumentsSimulatorProcess(Shell shell, IosApplicationBundle bundle, String deviceType) {
        String launchScript = getClass().getResource(APPLICATION_LAUNCH_SCRIPT).getPath();
        launch = shell.command("Launch Application with Instruments", launchScript)
                .withArgument(bundle.path())
                .withArgument(deviceType)
                .build();
        stopInstruments = killCommand(shell, "Stop Instruments", "instruments");
        stopSimulator = killCommand(shell, "Stop Simulator", "iPhone Simulator");
        stopSimulatedApplication = killCommand(shell, "Stop Simulated Application", bundle.executableName());
    }

    @Override
    public void start() {
        launch.run();
    }

    @Override
    public void stop() {
        stopInstruments.run();
        stopSimulator.run();
        stopSimulatedApplication.run();
    }

    private static RunnableCommand killCommand(Shell shell, String description, String processName) {
        return shell.command(description, "killall")
                .withArgument(processName)
                .build();
    }
}

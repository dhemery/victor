package com.dhemery.victor.device;

import com.dhemery.os.RunnableCommand;
import com.dhemery.os.Shell;
import com.dhemery.victor.IosApplicationBundle;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class InstrumentsSimulatorProcess implements Service {
    private static final String LAUNCH_SCRIPT_NAME = "launch-app-using-instruments.sh";
    private static final String LOOP_SCRIPT_NAME = "loop-forever.js";
    private final RunnableCommand launch;
    private final RunnableCommand stopSimulator;
    private final RunnableCommand stopSimulatedApplication;
    private final RunnableCommand stopInstruments;

    public InstrumentsSimulatorProcess(IosApplicationBundle bundle, String deviceType, Shell shell) {
        InputStream launchScriptStream = streamForResource(LAUNCH_SCRIPT_NAME);
        InputStream loopScriptStream = streamForResource(LOOP_SCRIPT_NAME);
        Path scriptDirectory = createScriptDirectory();
        Path launchScriptDestinationPath = scriptDirectory.resolve(LAUNCH_SCRIPT_NAME);
        Path loopScriptDestinationPath = scriptDirectory.resolve(LOOP_SCRIPT_NAME);

        copy(launchScriptStream, launchScriptDestinationPath);
        copy(loopScriptStream, loopScriptDestinationPath);

        launch = shell.command("Launch Application with Instruments", "bash")
                .withArgument(launchScriptDestinationPath.toString())
                .withArgument(bundle.path())
                .withArgument(deviceType)
                .build();
        stopInstruments = killCommand(shell, "Stop Instruments", "instruments");
        stopSimulator = killCommand(shell, "Stop Simulator", "iPhone Simulator");
        stopSimulatedApplication = killCommand(shell, "Stop Simulated Application", bundle.executableName());
    }

    private void copy(InputStream source, Path destination) {
        try {
            Files.copy(source, destination);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream streamForResource(String resourceName) {
        return getClass().getResourceAsStream(resourceName);
    }

    private Path createScriptDirectory() {
        try {
            return Files.createTempDirectory(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

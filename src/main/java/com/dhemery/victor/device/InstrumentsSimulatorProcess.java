package com.dhemery.victor.device;

import com.dhemery.os.RunnableCommand;
import com.dhemery.os.Shell;
import com.dhemery.victor.IosApplicationBundle;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class InstrumentsSimulatorProcess implements Service {
    private static final String AUTOMATION_TEMPLATE_NAME = "VictorLaunch.tracetemplate";
    private static final String OUTPUT_PREFIX = "instruments.output";
    private static final String AUTOMATION_TEMPLATE_PATH = createAutomationTemplate();
    private final RunnableCommand launch;
    private final RunnableCommand prepareLaunch;
    private final RunnableCommand stopSimulator;
    private final RunnableCommand stopSimulatedApplication;
    private final RunnableCommand stopInstruments;

    public InstrumentsSimulatorProcess(IosApplicationBundle bundle, String deviceType, Shell shell) {
        prepareLaunch = shell.command("Prepare Simulator Properties", "defaults")
                .withArguments("write", "com.apple.iphonesimulator", "SimulateDevice", "'" + deviceType + "'")
                .build();
        launch = shell.command("Launch Application with Instruments", "instruments")
                .withArguments("-D", OUTPUT_PREFIX)
                .withArguments("-t", AUTOMATION_TEMPLATE_PATH)
                .withArgument(bundle.pathToExecutable())
                .build();
        stopInstruments = killCommand(shell, "Stop Instruments", "instruments");
        stopSimulator = killCommand(shell, "Stop Simulator", "iPhone Simulator");
        stopSimulatedApplication = killCommand(shell, "Stop Simulated Application", bundle.executableName());
    }

    private static String createAutomationTemplate() {
        Path scriptDirectory = createScriptDirectory();
        Path destinationPath = scriptDirectory.resolve(AUTOMATION_TEMPLATE_NAME);
        InputStream resourceStream = InstrumentsSimulatorProcess.class.getResourceAsStream(AUTOMATION_TEMPLATE_NAME);
        copy(resourceStream, destinationPath);
        return destinationPath.toString();
    }

    private static void copy(InputStream source, Path destination) {
        try {
            Files.copy(source, destination);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Path createScriptDirectory() {
        try {
            return Files.createTempDirectory(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start() {
        prepareLaunch.run();
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

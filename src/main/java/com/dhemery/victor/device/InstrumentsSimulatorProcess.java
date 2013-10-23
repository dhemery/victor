package com.dhemery.victor.device;

import com.dhemery.os.Command;
import com.dhemery.os.Shell;
import com.dhemery.victor.IosApplicationBundle;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.dhemery.os.CommandBuilder.command;

public class InstrumentsSimulatorProcess implements Service {
    private static final String AUTOMATION_TEMPLATE_NAME = "VictorLaunch.tracetemplate";
    private static final String OUTPUT_PREFIX = "instruments.output";
    private static final String AUTOMATION_TEMPLATE_PATH = createAutomationTemplate();
    private final Command launch;
    private final Command prepareLaunch;
    private final Command stopSimulator;
    private final Command stopSimulatedApplication;
    private final Command stopInstruments;
    private final Shell shell;

    public InstrumentsSimulatorProcess(IosApplicationBundle bundle, String deviceType, Shell shell) {
        this.shell = shell;
        prepareLaunch = preLaunchCommand(deviceType);
        launch = launchCommand(bundle);
        stopInstruments = killCommand("Stop Instruments", "instruments");
        stopSimulator = killCommand("Stop Simulator", "iPhone Simulator");
        stopSimulatedApplication = killCommand("Stop Simulated Application", bundle.executableName());
    }

    @Override
    public void start() {
        run(prepareLaunch, launch);
    }

    @Override
    public void stop() {
        run(stopInstruments, stopSimulator, stopSimulatedApplication);
    }

    private void run(Command... commands) {
        for(Command command : commands) shell.run(command);
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

    private static Command killCommand(String description, String processName) {
        return command(description, "killall")
                .withArgument(processName)
                .build();
    }

    private static Command launchCommand(IosApplicationBundle bundle) {
        return command("Launch Application with Instruments", "instruments")
                .withArguments("-D", OUTPUT_PREFIX)
                .withArguments("-t", AUTOMATION_TEMPLATE_PATH)
                .withArgument(bundle.pathToExecutable())
                .build();
    }

    private static Command preLaunchCommand(String deviceType) {
        return command("Prepare Simulator Properties", "defaults")
                .withArguments("write", "com.apple.iphonesimulator", "SimulateDevice", "'" + deviceType + "'")
                .build();
    }
}

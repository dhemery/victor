package com.dhemery.victor.os;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * An command to run an AppleScript program on this computer.
 *
 * @author Dale Emery
 */
public class AppleScriptCommand implements Command {
    private static final String APPLESCRIPT_RUNNER = "osascript";
    private final ShellCommand command;

    /**
     * Construct a command to run the AppleScript program.
     */
    public AppleScriptCommand(String description) {
        command = new ShellCommand(description, APPLESCRIPT_RUNNER);
    }

    public AppleScriptCommand withLine(String line) {
        return withLines(line);
    }

    public AppleScriptCommand withLines(String... lines) {
        return withLines(Arrays.asList(lines));
    }

    /**
     * Transform the lines of an AppleScript program into a sequence
     * of arguments for the osascript command.
     *
     * @param scriptLines the lines of an AppleScript program.
     * @return a list of arguments to pass to osascript to run the AppleScript program.
     */
    public AppleScriptCommand withLines(List<String> scriptLines) {
        for (String line : scriptLines) {
            command.withArguments("-e", line);
        }
        return this;
    }

    @Override
    public List<String> arguments() {
        return command.arguments();
    }

    @Override
    public void buildTo(ProcessBuilder builder) {
        command.buildTo(builder);
    }

    @Override
    public String description() {
        return command.description();
    }

    @Override
    public Map<String,String> environment() {
        return command.environment();
    }

    @Override
    public String path() {
        return command.path();
    }
}

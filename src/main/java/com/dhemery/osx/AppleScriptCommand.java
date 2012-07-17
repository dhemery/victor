package com.dhemery.osx;

import com.dhemery.os.OSCommand;

import java.util.*;

/**
 * A command to run an AppleScript program.
 */
public class AppleScriptCommand implements OSCommand {
    private static final String APPLESCRIPT_RUNNER = "osascript";
    private static final String DEFAULT_DESCRIPTION = "(applescript program)";
    private final List<String> arguments = new ArrayList<String>();
    private String description = DEFAULT_DESCRIPTION;

    public AppleScriptCommand describedAs(String description) {
        this.description = description;
        return this;
    }
    public AppleScriptCommand withLine(String line) {
        arguments.add("-e");
        arguments.add(line);
        return this;
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
            withLine(line);
        }
        return this;
    }

    @Override
    public List<String> arguments() {
        return Collections.unmodifiableList(arguments);
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public Map<String,String> environment() {
        return Collections.emptyMap();
    }

    @Override
    public String path() {
        return APPLESCRIPT_RUNNER;
    }
}

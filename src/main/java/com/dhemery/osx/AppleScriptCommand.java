package com.dhemery.osx;

import com.dhemery.os.OSCommand;

import java.util.*;

/**
 * <p>
 * Builds a command to run an AppleScript script.
 * The command runs the OS X {@code osascript} command to execute the script.
 * </p>
 * <p>
 * A newly constructed {@code AppleScriptCommand}
 * has the default description {@code "(applescript)"}
 * and no lines of script.
 * </p>
 * <p>
 * To build the ApplesScript script,
 * call {@link #withLine(String)}, {@link #withLines(String...)}, and {@link #withLines(List)}
 * to append lines of script.
 * </p>
 * <p>
 * Many {@code AppleScriptCommand} methods are "builder" methods.
 * Each builder method returns {@code this},
 * so you can chain calls in a fluent builder expression.
 * </p>
 */
public class AppleScriptCommand implements OSCommand {
    private static final String APPLESCRIPT_RUNNER = "osascript";
    private static final String DEFAULT_DESCRIPTION = "(applescript)";
    private final List<String> arguments = new ArrayList<String>();
    private String description = DEFAULT_DESCRIPTION;

    /**
     * Set the script's description.
     * @param description the description of the script
     * @return this script command builder
     */
    public AppleScriptCommand describedAs(String description) {
        this.description = description;
        return this;
    }

    /**
     * Append a line to the script.
     * @param line the line to append
     * @return this script command builder
     */
    public AppleScriptCommand withLine(String line) {
        arguments.add("-e");
        arguments.add(line);
        return this;
    }

    /**
     * Append lines to the script.
     * @param lines the lines to append
     * @return this script command builder
     */
    public AppleScriptCommand withLines(String... lines) {
        return withLines(Arrays.asList(lines));
    }

    /**
     * Append lines to the script.
     * @param lines the lines to append
     * @return this script command builder
     */
    public AppleScriptCommand withLines(List<String> lines) {
        for (String line : lines) {
            withLine(line);
        }
        return this;
    }

    /**
     * The list of arguments to pass to {@code osascript}.
     * The list contains the lines added to the script,
     * in the order they were added,
     * with each line preceded  by a {@code "-e" argument}.
     * @return the list of arguments to pass to {@code osascript}.
     */
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

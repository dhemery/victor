package com.dhemery.victor.os;

import java.util.ArrayList;
import java.util.List;

/**
 * An command to run an AppleScript program on this computer.
 *
 * @author Dale Emery
 */
public class AppleScriptCommand extends OSCommand {
    private static final String APPLESCRIPT_RUNNER = "osascript";

    /**
     * Construct a command to run the AppleScript program.
     *
     * @param scriptLines the lines of the AppleScript program.
     */
    public AppleScriptCommand(List<String> scriptLines) {
        super(APPLESCRIPT_RUNNER, argumentsToRun(scriptLines));
    }

    /**
     * Transform the lines of an AppleScript program into a sequence
     * of arguments for the osascript command.
     *
     * @param scriptLines the lines of an AppleScript program.
     * @return a list of arguments to pass to osascript to run the AppleScript program.
     */
    private static List<String> argumentsToRun(List<String> scriptLines) {
        List<String> osascriptArguments = new ArrayList<String>();
        for (String line : scriptLines) {
            osascriptArguments.add("-e");
            osascriptArguments.add(line);
        }
        return osascriptArguments;
    }
}

package com.dhemery.os;

import java.util.List;
import java.util.Map;

/**
 * Represents command to run on the operating system.
 * The mechanism for invoking the command is left to implementors.
 */
public interface OSCommand {
    /**
     * A description of the command.
     * The discription neither affects nor is affected by execution of the command.
     */
    String description();

    /**
     * The path to the command's executable file.
     * The path is such that invoking it on the command line
     * would run the command.
     */
    String path();

    /**
     * The arguments for the command.
     */
    List<String> arguments();

    /**
     * The map of environment variables to add to the command's execution environment.
     * This map does not include environment variables that will be added by the operating system.
     */
    Map<String, String> environment();
}

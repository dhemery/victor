package com.dhemery.os;

import java.util.List;
import java.util.Map;

/**
 * An executable operating system command.
 */
public interface OSCommand {
    /**
     * A brief description of the command.
     * The description neither affects nor is affected by execution of the command.
     */
    String description();

    /**
     * The path to command's executable file.
     * The path is such that invoking it on a command line would run the command.
     */
    String path();

    /**
     * Arguments to pass to the command.
     */
    List<String> arguments();

    /**
     * Environment variables to add to the command's execution environment.
     * This map does not include environment variables supplied by the operating system.
     */
    Map<String,String> environment();
}

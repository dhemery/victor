package com.dhemery.os;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OSCommand {
    public final String path;
    public final List<String> arguments;
    public final Map<String, String> environment;
    public final String description;

    public OSCommand(String description, String path, List<String> arguments, Map<String, String> environment) {
        this.description = description;
        this.path = path;
        this.arguments = Collections.unmodifiableList(arguments);
        this.environment = Collections.unmodifiableMap(environment);
    }
}

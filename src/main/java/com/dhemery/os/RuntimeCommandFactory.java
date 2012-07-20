package com.dhemery.os;

import java.util.List;
import java.util.Map;

public class RuntimeCommandFactory implements OSCommandFactory<RunnableCommand> {
    @Override
    public RunnableCommand command(String description, String path, List<String> arguments, Map<String, String> environment) {
        return new RuntimeCommand(description, path, arguments, environment);
    }
}

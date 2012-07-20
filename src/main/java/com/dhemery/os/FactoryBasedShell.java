package com.dhemery.os;

public class FactoryBasedShell implements Shell {
    private final OSCommandFactory<RunnableCommand> factory;

    public FactoryBasedShell(OSCommandFactory<RunnableCommand> factory) {
        this.factory = factory;
    }

    @Override
    public OSCommandBuilder<RunnableCommand> command(String description, String path) {
        return new FactoryBasedCommandBuilder(factory, description, path);
    }
}

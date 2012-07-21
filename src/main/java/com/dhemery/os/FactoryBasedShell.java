package com.dhemery.os;

/**
 * A shell that obtains commands via a factory.
 */
public class FactoryBasedShell implements Shell {
    private final OSCommandFactory<RunnableCommand> factory;

    /**
     * Create a shell that uses the given factory to create commands.
     */
    public FactoryBasedShell(OSCommandFactory<RunnableCommand> factory) {
        this.factory = factory;
    }

    @Override
    public OSCommandBuilder<RunnableCommand> command(String description, String path) {
        return new FactoryBasedCommandBuilder(factory, description, path);
    }
}

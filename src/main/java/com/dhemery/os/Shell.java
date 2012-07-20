package com.dhemery.os;

public interface Shell {
    OSCommandBuilder<RunnableCommand> command(String description, String path);
}

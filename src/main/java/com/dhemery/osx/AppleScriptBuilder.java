package com.dhemery.osx;

import com.dhemery.os.OSCommandBuilder;
import com.dhemery.os.RunnableCommand;
import com.dhemery.os.Shell;
import com.google.common.base.Supplier;

import java.util.Arrays;
import java.util.List;

public class AppleScriptBuilder implements Supplier<RunnableCommand> {
    private final OSCommandBuilder<RunnableCommand> builder;

    public AppleScriptBuilder(Shell shell, String description) {
        builder = shell.command(description, "osascript");
    }

    /**
     * Append a line to the script.
     * @param line the line to append
     * @return this script command builder
     */
    public AppleScriptBuilder withLine(String line) {
        builder.withArguments("-e", line);
        return this;
    }

    /**
     * Append lines to the script.
     * @param lines the lines to append
     * @return this script command builder
     */
    public AppleScriptBuilder withLines(String... lines) {
        return withLines(Arrays.asList(lines));
    }

    /**
     * Append lines to the script.
     * @param lines the lines to append
     * @return this script command builder
     */
    public AppleScriptBuilder withLines(List<String> lines) {
        for (String line : lines) {
            withLine(line);
        }
        return this;
    }

    @Override
    public RunnableCommand get() {
        return builder.get();
    }
}

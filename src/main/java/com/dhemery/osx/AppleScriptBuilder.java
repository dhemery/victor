package com.dhemery.osx;

import com.dhemery.os.OSCommandBuilder;
import com.dhemery.os.OSCommand;

import java.util.Arrays;
import java.util.List;

public class AppleScriptBuilder {
    private final OSCommandBuilder builder;

    public AppleScriptBuilder(String description) {
        builder = new OSCommandBuilder(description, "osascript");
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

    public OSCommand build() {
        return builder.build();
    }
}

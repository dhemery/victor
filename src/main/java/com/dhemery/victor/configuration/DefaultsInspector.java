package com.dhemery.victor.configuration;

import com.dhemery.victor.os.OSCommand;

import java.util.Arrays;
import java.util.List;

/**
 * Fetches information about a "defaults" domain.
 */
public class DefaultsInspector implements Fetcher {
    @Override
    public String fetch(String domain, String item) {
        List<String> arguments = Arrays.asList("read", domain, item);
        OSCommand command = new OSCommand("defaults", arguments);
        return command.output();

    }
}

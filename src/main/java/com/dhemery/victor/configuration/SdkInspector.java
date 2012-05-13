package com.dhemery.victor.configuration;

import com.dhemery.victor.os.OSCommand;

import java.util.Arrays;
import java.util.List;

/**
 * Fetches information about an SDK.
 */
class SdkInspector implements Fetcher {
    @Override
    public String fetch(String sdkCanonicalName, String itemName) {
        List<String> arguments = Arrays.asList("-sdk", sdkCanonicalName, "-version", itemName);
        OSCommand command = new OSCommand("xcodebuild", arguments);
        return command.output();
    }
}

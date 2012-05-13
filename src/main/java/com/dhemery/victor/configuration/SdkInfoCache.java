package com.dhemery.victor.configuration;

import com.dhemery.victor.configuration.generic.CacheSource;
import com.dhemery.victor.configuration.generic.ContextItem;
import com.dhemery.victor.configuration.generic.ContextItemCache;
import com.dhemery.victor.os.OSCommand;

import java.util.Arrays;
import java.util.List;

public class SdkInfoCache extends ContextItemCache {
    public SdkInfoCache() {
        super(sdkInfoSource());
    }

    private static CacheSource<ContextItem,String> sdkInfoSource() {
        return new CacheSource<ContextItem,String>() {
            @Override
            public String value(ContextItem item) {
                List<String> arguments = Arrays.asList("-sdk", item.context(), "-version", item.name());
                OSCommand command = new OSCommand("xcodebuild", arguments);
                return command.output();
            }
        };
    }
}

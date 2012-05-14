package com.dhemery.victor.configuration;

import com.dhemery.configuration.CacheSource;
import com.dhemery.configuration.ContextItem;
import com.dhemery.configuration.ContextItemCache;
import com.dhemery.victor.os.OSCommand;

import java.util.Arrays;
import java.util.List;

public class DefaultsCache extends ContextItemCache {
    public DefaultsCache() {
        super(defaults());
    }

    private static CacheSource<ContextItem,String> defaults() {
        return new CacheSource<ContextItem,String>() {
            @Override
            public String value(ContextItem item) {
                List<String> arguments = Arrays.asList("read", item.context(), item.name());
                OSCommand command = new OSCommand("defaults", arguments);
                return command.output();
            }
        };
    }
}

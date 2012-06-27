package com.dhemery.victor.os;

import com.dhemery.configuration.CacheSource;
import com.dhemery.configuration.ContextItem;
import com.dhemery.configuration.ContextItemCache;

import java.util.Arrays;
import java.util.List;

/**
 * A cache of information obtained from the Mac OS X {@code defaults} command.
 */
public class DefaultsCache extends ContextItemCache {
    public DefaultsCache() {
        super(defaults());
    }

    private static CacheSource<ContextItem,String> defaults() {
        return new CacheSource<ContextItem,String>() {
        /**
         * <p>
         * Retrieve the value of an "default" for a domain.
         * The value of {@code item.context()} is the "domain" of the default.
         * The value of {@code item.name()} is the name of default in the domain.
         * </p>
         * <p>
         * See the manual page and output of {@code defaults read}
         * for examples of what information can be retrieved.
         * </p>
         * @param item the domain and name of the default to retrieve.
         * @return the value of the default for the domain.
         */
        @Override
            public String value(ContextItem item) {
                List<String> arguments = Arrays.asList("read", item.context(), item.name());
                OSCommand command = new OSCommand("defaults", arguments);
                return command.output();
            }
        };
    }
}

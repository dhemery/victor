package com.dhemery.victor.os;

import com.dhemery.configuration.CacheSource;
import com.dhemery.configuration.ContextItem;
import com.dhemery.configuration.ContextItemCache;

/**
 * A cache of information obtained from the Mac OS X {@code defaults} command.
 */
public class DefaultsCache extends ContextItemCache {
    private DefaultsCache(Shell shell) {
        super(defaults(shell));
    }

    private static CacheSource<ContextItem,String> defaults(final Shell shell) {
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
                return shell.outputFrom(new ShellCommand("defaults").withArguments("read", item.context(), item.name()));
            }
        };
    }
}

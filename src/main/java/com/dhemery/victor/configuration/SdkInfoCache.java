package com.dhemery.victor.configuration;

import com.dhemery.configuration.CacheSource;
import com.dhemery.configuration.ContextItem;
import com.dhemery.configuration.ContextItemCache;
import com.dhemery.victor.os.OSCommand;

import java.util.Arrays;
import java.util.List;

/**
 * A cache of SDK information obtained from the Mac OS X {@code xcodebuild} command.
 */
public class SdkInfoCache extends ContextItemCache {
    public SdkInfoCache() {
        super(sdkInfoSource());
    }

    private static CacheSource<ContextItem,String> sdkInfoSource() {
        return new CacheSource<ContextItem,String>() {
            /**
             * <p>
             * Retrieve the value of an "infoitem" for an SDK.
             * The value of {@code item.context()} is the canonical name of the SDK.
             * The value of {@code item.name()} is the name of an SDK infoitem.
             * </p>
             * <p>
             * See the manual page and output of {@code xcodebuild -sdk -version}
             * for information about what infoitems can be retrieved.
             * </p>
             * @param item the SDK canonical name and item name to retrieve.
             * @return the value of the item for the SDK.
             */
            @Override
            public String value(ContextItem item) {
                List<String> arguments = Arrays.asList("-sdk", item.context(), "-version", item.name());
                OSCommand command = new OSCommand("xcodebuild", arguments);
                return command.output();
            }
        };
    }
}

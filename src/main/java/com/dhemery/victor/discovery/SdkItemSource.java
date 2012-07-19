package com.dhemery.victor.discovery;

import com.dhemery.configuration.CacheSource;
import com.dhemery.os.OSCommandSubscriber;
import com.dhemery.os.PublishingCommandBuilder;

/**
 * Discovers information about iOS SDKs in the active development environment.
 */
public class SdkItemSource implements CacheSource<SdkItemKey,String> {
    private final OSCommandSubscriber publisher;

    public SdkItemSource(OSCommandSubscriber publisher) {
        this.publisher = publisher;
    }

    /**
     * <p>
     * Retrieve the value of an "infoitem" for an iOS SDK.
     * </p>
     * <p>
     * See the manual page and output of {@code xcodebuild -sdk -version}
     * for information about what infoitems can be retrieved.
     * </p>
     * @param key identifies the item to retrieve and the iOS SDK from which to retrieve it.
     * @return the value of the item for the SDK.
     */
    @Override
    public String value(SdkItemKey key) {
        return new PublishingCommandBuilder(publisher, "Request SDK Information", "xcodebuild")
                .withArguments("-sdk", key.sdkname(), "-version", key.infoitem())
                .build()
                .run()
                .output();
    }
}

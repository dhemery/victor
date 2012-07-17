package com.dhemery.victor.discovery;

import com.dhemery.configuration.CacheSource;
import com.dhemery.os.OSCommand;
import com.dhemery.os.Shell;
import com.dhemery.os.ShellCommand;

/**
 * Discovers information about iOS SDKs in the active development environment.
 * Retrieves the information by running {@code xcodebuild} via a {@link Shell}.
 */
public class SdkItemSource implements CacheSource<SdkItemKey,String> {
    private final Shell shell;

    /**
     * @param shell the shell that will run commands for this source.
     */
    public SdkItemSource(Shell shell) {
        this.shell = shell;
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
        OSCommand command = new ShellCommand("xcodebuild")
                                .withArguments("-sdk", key.sdkname(), "-version", key.infoitem())
                                .describedAs("Request SDK Information");
        return shell.outputFrom(command);
    }
}

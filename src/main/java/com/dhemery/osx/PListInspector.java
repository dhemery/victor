package com.dhemery.osx;

import com.dhemery.configuration.JsonInspector;
import com.dhemery.os.OSCommandSubscriber;
import com.dhemery.os.PublishingCommandBuilder;

/**
 * A JSON inspector for properties read from a plist file.
 */
public class PListInspector extends JsonInspector {
    /**
     * @param path the absolute path to the plist file to read.
     */
    public PListInspector(OSCommandSubscriber publisher, String path) {
        super(plistAsJson(publisher, path));
    }

    private static String plistAsJson(OSCommandSubscriber publisher, String path) {
        return new PublishingCommandBuilder(publisher, "Read PList", "plutil")
                                .withArguments("-convert", "json", "-o", "-", "--", path)
                                .build()
                                .run()
                                .output();
    }
}

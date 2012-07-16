package com.dhemery.victor.discovery;

/**
 * Identifies an item of information about an iOS SDK.
 */
public class SdkItemKey {
    private final String sdkname;
    private final String infoitem;

    /**
     * Create a key for an infoitem in an iOS SDK.
     * @param sdkname the canonical name of an iOS SDK.
     * @param infoitem the name of an infoitem.
     */
    public SdkItemKey(String sdkname, String infoitem) {
        this.sdkname = sdkname;
        this.infoitem = infoitem;
    }

    /**
     * The name of the key's infoitem.
     */
    public String infoitem() {
        return infoitem;
    }

    /**
     * The canonical name of the key's iOS SDK.
     */
    public String sdkname() {
        return sdkname;
    }
}

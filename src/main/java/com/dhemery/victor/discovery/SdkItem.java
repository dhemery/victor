package com.dhemery.victor.discovery;

/**
 * Identifies an item of information about an iOS SDK.
 */
public class SdkItem {
    private final String sdkname;
    private final String infoitem;

    /**
     * @param sdkname the canonical name of an iOS SDK.
     * @param infoitem the name of an infoitem.
     */
    public SdkItem(String sdkname, String infoitem) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SdkItem sdkItem = (SdkItem) o;

        if (!infoitem.equals(sdkItem.infoitem)) return false;
        if (!sdkname.equals(sdkItem.sdkname)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sdkname.hashCode();
        result = 31 * result + infoitem.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SdkItem{" +
                "sdkname='" + sdkname + '\'' +
                ", infoitem='" + infoitem + '\'' +
                '}';
    }
}

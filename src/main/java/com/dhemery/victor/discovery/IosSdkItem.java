package com.dhemery.victor.discovery;

public class IosSdkItem {
    private final String sdkCanonicalName;
    private final String itemName;

    public IosSdkItem(String sdkCanonicalName, String itemName) {
        this.sdkCanonicalName = sdkCanonicalName;
        this.itemName = itemName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IosSdkItem sdkItem = (IosSdkItem) o;

        if (itemName != null ? !itemName.equals(sdkItem.itemName) : sdkItem.itemName != null) return false;
        if (sdkCanonicalName != null ? !sdkCanonicalName.equals(sdkItem.sdkCanonicalName) : sdkItem.sdkCanonicalName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sdkCanonicalName != null ? sdkCanonicalName.hashCode() : 0;
        result = 31 * result + (itemName != null ? itemName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SdkItem{" +
                "sdkCanonicalName='" + sdkCanonicalName + '\'' +
                ", itemName='" + itemName + '\'' +
                '}';
    }
}

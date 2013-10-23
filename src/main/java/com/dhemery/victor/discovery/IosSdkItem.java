package com.dhemery.victor.discovery;

public class IosSdkItem {
    private final String sdkName;
    private final String itemName;

    public IosSdkItem(String sdkName, String itemName) {
        this.sdkName = sdkName;
        this.itemName = itemName;
    }

    public String sdkName() {
        return sdkName;
    }

    public String itemName() {
        return itemName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IosSdkItem sdkItem = (IosSdkItem) o;

        if (itemName != null ? !itemName.equals(sdkItem.itemName) : sdkItem.itemName != null) return false;
        if (sdkName != null ? !sdkName.equals(sdkItem.sdkName) : sdkItem.sdkName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sdkName != null ? sdkName.hashCode() : 0;
        result = 31 * result + (itemName != null ? itemName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SdkItem{" +
                "sdkCanonicalName='" + sdkName + '\'' +
                ", itemName='" + itemName + '\'' +
                '}';
    }
}

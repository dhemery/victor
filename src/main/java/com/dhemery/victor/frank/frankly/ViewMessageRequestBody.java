package com.dhemery.victor.frank.frankly;

import com.dhemery.victor.By;
import com.google.gson.annotations.SerializedName;

/**
 * A request to send a message to the views identified a query.
 *
 * @author Dale Emery
 */
public class ViewMessageRequestBody extends MessageRequestBody {
    @SerializedName("selector_engine")
    private final String engine;
    @SerializedName("query")
    private final String pattern;

    /**
     * @param by   identifies the views that will receive the message.
     * @param message the message to send to the views.
     */
    public ViewMessageRequestBody(By by, Message message) {
        super(message);
        engine = by.engine();
        pattern = by.pattern();
    }

    @Override
    public String toString() {
        return String.format("%s:%s %s", engine, pattern, super.toString());
    }
}

package com.dhemery.victor.frank.frankly;

import com.dhemery.victor.http.HttpPostBody;
import com.google.gson.annotations.SerializedName;

/**
 * Carries a message to an object in an iOS application.
 *
 * @author Dale Emery
 */
public class MessageRequestBody extends HttpPostBody {
    @SerializedName("operation")
    private final Message message;

    public MessageRequestBody(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message.toString();
    }
}

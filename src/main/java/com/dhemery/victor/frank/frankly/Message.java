package com.dhemery.victor.frank.frankly;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A message to send objects in an iOS application.
 * A message is specified by a name and a list of arguments.
 *
 * @author Dale Emery
 */
public class Message {
    @SerializedName("method_name")
    private final String name;
    private final List<Object> arguments = new ArrayList<Object>();

    /**
     * @param name      the name of the message to send.
     * @param arguments arguments to send with the message.
     */
    public Message(String name, Object... arguments) {
        this.name = name;
        this.arguments.addAll(Arrays.asList(arguments));
    }

    @Override
    public String toString() {
        return String.format("%s%s", name, arguments);
    }
}

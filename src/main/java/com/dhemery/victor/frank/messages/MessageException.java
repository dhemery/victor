package com.dhemery.victor.frank.messages;


public class MessageException extends RuntimeException {
    public MessageException(Object receiver, Message message, MessageResponse response) {
        super(errorMessageFor(receiver, message, response));
    }

    private static String errorMessageFor(Object receiver, Message message, MessageResponse response) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("Error response from ").append(receiver).append('\n')
                .append("Message was:\n").append(message.toString()).append('\n')
                .append("Response was:\n").append(response.toString()).append('\n');
        return errorMessage.toString();
    }
}

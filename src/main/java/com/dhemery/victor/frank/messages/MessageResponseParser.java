package com.dhemery.victor.frank.messages;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Constructs a {@link MessageResponse} from a properly formatted JSON string.
 *
 * @author Dale Emery
 */
public class MessageResponseParser implements JsonDeserializer<MessageResponse> {
    @Override
    public MessageResponse deserialize(JsonElement element, Type type, JsonDeserializationContext context) {
        List<String> results = new ArrayList<String>();
        String reason = "";
        String details = "";

        JsonObject body = element.getAsJsonObject();
        boolean succeeded = body.get("outcome").getAsString().equals("SUCCESS");

        if (succeeded) {
            if (body.get("results") != null) {
                for (JsonElement result : body.get("results").getAsJsonArray()) {
                    if (result.isJsonNull()) results.add(null);
                    else results.add(result.getAsString());
                }
            }
        } else {
            reason = body.get("reason").getAsString();
            details = body.get("details").getAsString();
        }
        return new MessageResponse(succeeded, results, reason, details);
    }
}

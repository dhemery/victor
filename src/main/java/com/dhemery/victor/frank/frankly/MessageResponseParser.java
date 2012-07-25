package com.dhemery.victor.frank.frankly;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Deserializes a {@link MessageResponse} from a properly formatted JSON string.
 * The serialized JSON format of a message response is defined in the "Frankly wire protocol."
 * See the Frank website for details.
 */
public class MessageResponseParser implements JsonDeserializer<MessageResponse> {
    /**
     * Create a {@code MessageResponse} object from a serialized JSON representation.
     */
    @Override
    public MessageResponse deserialize(JsonElement element, Type alsoIgnored, JsonDeserializationContext ignored) {
        List<String> results = new ArrayList<String>();
        String reason = "";
        String details = "";

        JsonObject body = element.getAsJsonObject();
        boolean succeeded = body.get("outcome").getAsString().equals("SUCCESS");

        if (succeeded) {
            if (body.get("results") != null) {
                for (JsonElement result : body.get("results").getAsJsonArray()) {
                    if(result.isJsonNull()) results.add(null);
                    else if(result.isJsonObject()) results.add(result.toString());
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

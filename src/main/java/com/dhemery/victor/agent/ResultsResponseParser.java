package com.dhemery.victor.agent;

import com.dhemery.victor.view.OperationResponse;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Constructs a {@link com.dhemery.victor.view.OperationResponse} from a properly formatted JSON string.
 * @author Dale Emery
 *
 */
public class ResultsResponseParser implements JsonDeserializer<OperationResponse> {
	@Override
	public OperationResponse deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
		List<String> results = new ArrayList<String>();
		String reason = "";
		String details = "";

		JsonObject body = element.getAsJsonObject();
		boolean succeeded = body.get("outcome").getAsString().equals("SUCCESS");

		if(succeeded) {
			for(JsonElement result : body.get("results").getAsJsonArray()) {
				if(result.isJsonNull()) results.add(null);
				else results.add(result.getAsString());
			}
		} else {
			reason = body.get("reason").getAsString();
			details = body.get("details").getAsString();
		}
		return new OperationResponse(succeeded, results, reason, details);
	}
}

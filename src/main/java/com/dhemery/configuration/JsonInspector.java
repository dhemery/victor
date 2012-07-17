package com.dhemery.configuration;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.*;

//todo: Describe paths.
//todo: Throw exception instead of returning null. Will need to give users a way to determine whether an element exists.
/**
 * Retrieves information from a JSON representation of an object.
 */
public class JsonInspector {
    private final JsonElement root;

    /**
     * @param jsonString the JSON representation of the object to inspect.
     */
    public JsonInspector(String jsonString) {
        root = new JsonParser().parse(jsonString);
    }

    /**
     * The names of the name/value pairs in a JSON object.
     * @param path the path to the JSON object
     */
    public Set<String> names(Object... path) {
        JsonElement element = element(root, path);
        if(element == null || !element.isJsonObject()) return Collections.emptySet();
        Set<Map.Entry<String, JsonElement>> entries = element.getAsJsonObject().entrySet();
        Set<String> keys = new HashSet<String>();
        for(Map.Entry<String,JsonElement> entry : entries) {
            keys.add(entry.getKey());
        }
        return keys;
    }

    /**
     * The number of values in a JSON array.
     * @param path the path to the JSON array
     */
    public Integer size(Object... path) {
        JsonElement element = element(root, path);
        if(element == null || !element.isJsonArray()) return 0;
        return element.getAsJsonArray().size();
    }

    /**
     * The string representation of a JSON value.
     * @param path the path to the JSON value
     */
    public String stringValue(Object... path) {
        return asString(element(root, path));
    }

    private JsonElement child(JsonElement element, Object path) {
        if(path instanceof String) return childByName(element, (String) path);
        if(path instanceof Integer) return childByIndex(element, (Integer) path);
        return null;
    }

    private JsonElement childByIndex(JsonElement element, Integer index) {
        if(!element.isJsonArray()) return null;
        JsonArray array = element.getAsJsonArray();
        if(index >= array.size()) return null;
        return array.get(index);
    }

    private JsonElement childByName(JsonElement element, String name) {
        if(!element.isJsonObject()) return null;
        JsonObject object = element.getAsJsonObject();
        if(!object.has(name)) return null;
        return object.get(name);
    }

    private JsonElement element(JsonElement element, Object...specifiers) {
        JsonElement specifiedElement = element;
        for(Object specifier : specifiers) {
            specifiedElement = child(specifiedElement, specifier);
            if(specifiedElement == null)  return null;
        }
        return specifiedElement;
    }

    private String asString(JsonElement element) {
        if(element == null) return null;
        return element.getAsString();
    }
}

package com.dhemery.configuration;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.*;

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
     * Retrieve the names of the objects inside the target object.
     * @param path the path to the target object
     */
    public List<String> names(Object... path) {
        JsonElement element = element(root, path);
        if(element == null || !element.isJsonObject()) return Collections.emptyList();
        Set<Map.Entry<String, JsonElement>> entries = element.getAsJsonObject().entrySet();
        List<String> keys = new ArrayList<String>();
        for(Map.Entry<String,JsonElement> entry : entries) {
            keys.add(entry.getKey());
        }
        return keys;
    }

    /**
     * The number of objects inside a target object.
     * @param path the path to the target object
     */
    public Integer size(Object... path) {
        JsonElement element = element(root, path);
        if(element == null || !element.isJsonArray()) return 0;
        return element.getAsJsonArray().size();
    }

    /**
     * The string value of a target object.
     * @param path the path to the target object
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

package com.dhemery.victor.discovery;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.*;

@SuppressWarnings({"UnusedDeclaration", "ReturnOfNull"})
public class JsonInspector {
    private final JsonElement root;

    public JsonInspector(String jsonString) {
        root = new JsonParser().parse(jsonString);
    }

    private JsonElement child(JsonElement element, Object specifier) {
        if(specifier instanceof String) return childByName(element, (String) specifier);
        if(specifier instanceof Integer) return childByIndex(element, (Integer) specifier);
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
            specifiedElement = child(element, specifier);
            if(specifiedElement == null) return null;
        }
        return specifiedElement;
    }

    public List<String> names(Object... specifiers) {
        JsonElement element = element(root, specifiers);
        if(element == null || !element.isJsonObject()) return Collections.emptyList();
        Set<Map.Entry<String, JsonElement>> entries = element.getAsJsonObject().entrySet();
        List<String> keys = new ArrayList<String>();
        for(Map.Entry<String,JsonElement> entry : entries) {
            keys.add(entry.getKey());
        }
        return keys;
    }

    public Integer size(Object... specifiers) {
        JsonElement element = element(root, specifiers);
        if(element == null || !element.isJsonArray()) return 0;
        return element.getAsJsonArray().size();
    }

    public String stringValue(Object... specifiers) {
        return asString(element(root, specifiers));
    }

    private String asString(JsonElement element) {
        if(element == null) return null;
        return element.getAsString();
    }
}

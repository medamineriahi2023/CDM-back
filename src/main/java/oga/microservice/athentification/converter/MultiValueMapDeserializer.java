package oga.microservice.athentification.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiValueMapDeserializer extends StdDeserializer<MultiValueMap<String, String>> {
    public MultiValueMapDeserializer() {
        this(null);
    }

    protected MultiValueMapDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public MultiValueMap<String, String> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String criteria = p.readValueAsTree().toString();
        return convertStringToMultiValueMap(criteria);
    }

    public static MultiValueMap<String, String> convertStringToMultiValueMap(String criteria) {
        if (criteria == null) {
            return new LinkedMultiValueMap<>();
        }
        Gson gson = new Gson();
        JsonObject object = gson.fromJson(criteria, JsonObject.class);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        for (String key : object.keySet()) {
            JsonElement value = object.get(key);
            if (value.isJsonArray()) {
                JsonArray array = value.getAsJsonArray();
                List<String> values = new ArrayList<>();
                array.forEach(val -> values.add(val.isJsonNull() ? "null" : val.getAsString()));
                map.put(key, values);
            } else {
                map.put(key, Collections.singletonList(value.getAsString()));
            }
        }
        return map;
    }
}

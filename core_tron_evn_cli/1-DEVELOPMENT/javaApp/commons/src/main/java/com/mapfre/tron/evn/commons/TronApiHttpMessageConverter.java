package com.mapfre.tron.evn.commons;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;


public class TronApiHttpMessageConverter extends GsonHttpMessageConverter {

    Gson gson;

    public TronApiHttpMessageConverter() {
        super();
        this.setGson(getGson());
    }

    public Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder().registerTypeHierarchyAdapter(Date.class, new DateTypeAdapter())
                    .create();
        }
        return gson;
    }

    private static class DateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            if (src == null) {
                return null;
            } else {
                return new JsonPrimitive(src.getTime());
            }
        }

        public Date deserialize(JsonElement json, Type type, JsonDeserializationContext cxt) {
            Date result = null;
            if (json != null) {
                try {// milliseconds
                    result = new Date(json.getAsLong());
                } catch (NumberFormatException nfe) {// ISO 8601
                    TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse(json.getAsString());
                    Instant i = Instant.from(ta);
                    return Date.from(i);
                }
            }
            return result;
        }
    }
}